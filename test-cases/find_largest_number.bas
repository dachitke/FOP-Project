Dim num As Integer = 3947
Dim temp As Integer = num
Dim largest As Integer = 0
Dim digit As Integer = 0

While temp > 0
    digit = temp Mod 10
    If digit > largest Then
        largest = digit
    End If
    temp = temp \ 10
Wend

print largest