import os, csv


def write_content_by_lines(file_path: str, content):
    """
    写入文本内容，覆盖式
    @param file_path:
    @param content:
    """
    with open(file_path, "w", encoding="UTF-8") as file_name:
        # for text in content:
        #     file_name.writelines(text)
        file_name.writelines(content)
        file_name.close()


def write_content_by_append_lines(file_path: str, content):
    """
    写入文本内容：追加式
    @param file_path:
    @param content:
    """
    with open(file_path, "a", encoding="UTF-8") as file_name:
        file_name.writelines(content)
        file_name.close()


def write_content_by_top_line(file_path: str, content):
    """
    写入文本内容：头部插入式
    @param file_path:
    @param content:
    """
    with open(file_path, "a+", encoding="UTF-8") as file_name:
        old = file_name.read()
        file_name.seek(0)
        file_name.writelines(content)
        file_name.writelines(old)
        file_name.close()


def read_dir_files_name(file_dir: str):
    """
    读取目录下的文件列表
    @param file_dir:
    @return:
    """
    for root, dirs, files in os.walk(file_dir):
        return files
    return None


def read_file_lines(file_path: str, encoding="UTF-8"):
    """
    读取文本文件，按行读取
    @param file_path:
    @return:
    """
    content_list = []
    with open(file_path, 'r', encoding=encoding) as file:
        for line in file:
            content_list.append(line)
        file.close()
    return content_list


def read_file(file_path: dir, encoding="UTF-8"):
    """
    读取文本文件，按行读取
    @param encoding:
    @param file_path:
    @return:
    """
    with open(file_path, 'r', encoding=encoding) as file:
        content = file.read()
    file.close()
    return content


class Excel(object):
    def __init__(self):
        pass

    def read_file(self, file_path) -> []:
        content = []
        with open(file_path, 'r', newline='') as cvs_file:
            reader = csv.reader(cvs_file)
            for row in reader:
                content.append(','.join(row))
        return content
