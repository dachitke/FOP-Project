Dim num as Integer = 29
Dim isPrime as Integer = 1
Dim i as Integer = 2

While i <= num \ 2
    If num Mod i = 0 Then
        isPrime = 0
        Exit While
    End If
    i = i + 1
Wend

print isPrime
