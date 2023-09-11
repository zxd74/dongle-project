# 线程池配置
import contextlib
import queue, threading

# 创建空对象,用于停止线程
StopEvent = object()


class ThreadPool:
    def __init__(self, max_num: int, max_task_num: int = None, name=None):
        self.max_num = max_num
        if name:
            self.name = name
        else:
            self.name = "thread pool"
        if max_task_num:
            self.queue = queue.Queue(max_task_num)
        else:
            # 默认队列可接受无限多个的任务
            self.queue = queue.Queue()
        # 任务取消标识
        self.cancel = False
        # 任务中断标识
        self.terminal = False
        # 已实例化的线程列表
        self.generate_list = []
        # 处于空闲状态的线程列表
        self.free_list = []

    def put(self, func, args, callback=None):
        if self.cancel:
            return
        # 如果没有空闲的线程，并且已创建的线程的数量小于预定义的最大线程数，则创建新线程。
        if len(self.free_list) == 0 and len(self.generate_list) < self.max_num:
            self.generate_thread()
        # 构造任务参数元组，分别是调用的函数，该函数的参数，回调函数。
        w = (func, args, callback)
        # 将任务放入队列
        self.queue.put(w)

    def generate_thread(self):
        # 每个线程都执行call方法
        t = threading.Thread(target=self.call)
        t.start()

    def call(self):
        # 获取当前线程的名字
        current_thread = threading.currentThread().getName()
        # 将当前线程的名字加入已实例化的线程列表中
        self.generate_list.append(current_thread)
        # 从任务队列中获取一个任务
        event = self.queue.get()
        # TODO 线程池最大限制未生效，队列限制大小，但最大线程数超过队列会同步
        # TODO 增加工作线程监听，有空闲时，处理下一个队列
        # 让获取的任务不是终止线程的标识对象时
        while event != StopEvent:
            # 解析任务中封装的三个参数
            func, arguments, callback = event
            # 抓取异常，防止线程因为异常退出
            try:
                # 正常执行任务函数
                result = func(*arguments)
                success = True
            except Exception as e:
                # 当任务执行过程中弹出异常
                result = None
                success = False
            # 如果有指定的回调函数
            if callback is not None:
                # 执行回调函数，并抓取异常
                try:
                    callback(success, result)
                except Exception as e:
                    pass
            # 当某个线程正常执行完一个任务时，先执行worker_state方法
            with self.worker_state(self.free_list, current_thread):
                # 如果强制关闭线程的flag开启，则传入一个StopEvent元素
                if self.terminal:
                    event = StopEvent
                # 否则获取一个正常的任务，并回调worker_state方法的yield语句
                else:
                    # 从这里开始又是一个正常的任务循环
                    event = self.queue.get()
        else:
            # 一旦发现任务是个终止线程的标识元素，将线程从已创建线程列表中删除
            self.generate_list.remove(current_thread)

    def close(self):
        """
        执行完所有的任务后，让所有线程都停止的方法
        """
        # 设置flag
        self.cancel = True
        # 计算已创建线程列表中线程的个数，然后往任务队列里推送相同数量的终止线程的标识元素
        full_size = len(self.generate_list)
        while full_size:
            self.queue.put(StopEvent)
            full_size -= 1

    def terminate(self):
        """
        在任务执行过程中，终止线程，提前退出。
        """
        self.terminal = True
        # 强制性的停止线程
        while self.generate_list:
            self.queue.put(StopEvent)

    @contextlib.contextmanager
    def worker_state(self, state_list, worker_thread):
        """
        用于记录空闲的线程，或从空闲列表中取出线程处理任务
        """
        # 将当前线程，添加到空闲线程列表中
        state_list.append(worker_thread)
        # 捕获异常
        try:
            # 在此等待
            yield
        finally:
            # 将线程从空闲列表中移除
            state_list.remove(worker_thread)
