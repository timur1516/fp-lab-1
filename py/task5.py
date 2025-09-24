def gcd(a, b):
    if b == 0:
        return a
    return gcd(b, a % b)

def lcm(a, b):
    return a * b // gcd(a, b)

def smallest_multiple(n):
    ans = 1
    for i in range(1, n + 1):
        ans = lcm(ans, i)
    return ans

print(smallest_multiple(20))