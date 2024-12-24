Dim num As Integer = 121
Dim temp As Integer
Dim reverse As Integer
Dim remainder As Integer

reverse = 0
temp = num


While temp > 0
    remainder = temp Mod 10
    reverse = reverse * 10 + remainder
    temp = temp \ 10
Wend


If reverse = num Then
    print "Palindrome"
Else
    print "Not Palindrome"
End If
