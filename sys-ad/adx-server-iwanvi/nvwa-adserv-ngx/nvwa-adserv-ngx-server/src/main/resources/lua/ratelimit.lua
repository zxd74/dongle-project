local current,cur_val,limit
limit = ARGV[2]

if limit == nil then
    return -3
end

-- 首先获取当前数值,如果超过最大限制则返回1
local key_exists = redis.call("exists",KEYS[1])

if key_exists == 0 then
    current = redis.call("incr",KEYS[1])
    redis.call("expire",KEYS[1],ARGV[1])
    return 0
else
    current = redis.call("get",KEYS[1])
    if current >= limit then
        return 1
    else
        redis.call("incr",KEYS[1])
        return 0
    end
end