Dim num as Integer = 12345
Dim reverse as Integer = 0
Dim remainder as Integer = 0

While num <> 0
    remainder = num Mod 10
    reverse = reverse * 10 + remainder
    num = num \ 10
Wend

print reverse