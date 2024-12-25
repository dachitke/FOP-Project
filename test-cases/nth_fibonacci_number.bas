Dim n As Integer = 10
Dim a As Integer = 0
Dim b As Integer = 1
Dim fib As Integer = 0
Dim i As Integer = 2


If n = 1 Then
    fib = a
If n = 2 Then
    fib = b
Else
    While i < n
        fib = a + b
        a = b
        b = fib
        i = i + 1
    Wend
End If

print fib