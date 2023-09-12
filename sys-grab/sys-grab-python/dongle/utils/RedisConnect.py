import redis


def redis_connect(host, password, port):
    return redis.Redis(host=host, password=password, port=port)