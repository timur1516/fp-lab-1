def cycle_length(n):
    reminder = 1
    position = 0
    used = {}
    
    while reminder != 0:
        if reminder in used:
            return position - used[reminder]
        used[reminder] = position
        reminder = (reminder * 10) % n
        position += 1
    
    return 0

def reciprocal_cycles(n):
    max_length = 0
    value = 1
    for i in range(1, n + 1):
        cur_length = cycle_length(i)
        if cur_length > max_length:
            max_length = cur_length
            value = i
    return value

print(reciprocal_cycles(1000))