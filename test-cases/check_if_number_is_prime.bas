Dim num as Integer = 29
Dim isPrime as Boolean = True
Dim i as Integer = 2

While i <= num \ 2
    If num Mod i = 0 Then
        isPrime = False
        Exit While
    End If
    i = i + 1
Wend

If isPrime  Then
    print "Prime"
Else
    print "Not Prime"
End If