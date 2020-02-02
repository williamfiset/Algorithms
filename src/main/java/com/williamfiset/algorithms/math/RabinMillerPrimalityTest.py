
import random
# Rabin_Miller primality check. Tests whether or not a number
# is prime with a failure rate of: (1/2)^certainty
def isPrime(n, certainty = 12 ):
    if(n < 2): return False
    if(n != 2 and (n & 1) == 0): return False
    s = n-1
    while((s & 1) == 0): s >>= 1
    for _ in range(certainty):
        r = random.randrange(n-1) + 1
        tmp = s
        mod = pow(r,tmp,n)
        while(tmp != n-1 and mod != 1 and mod != n-1):
            mod = (mod*mod) % n
            tmp <<= 1
        if (mod != n-1 and  (tmp & 1) == 0): return False
    return True

print(isPrime(5))
print(isPrime(1433))
print(isPrime(567887653))
print(isPrime(75611592179197710043))
print(isPrime(205561530235962095930138512256047424384916810786171737181163))
