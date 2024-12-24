Dim num As Integer = 1234
Dim temp As Integer = num
Dim sum As Integer = 0
Dim digit As Integer = 0


While temp > 0
    digit = temp Mod 10
    sum = sum + digit
    temp = temp \ 10
Wend

print sum