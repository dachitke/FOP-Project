Dim A as Integer = 56
Dim B as Integer = 98
Dim temp as Integer = 0

While B <> 0
    temp = B
    B = A Mod B
    A = temp
Wend

print A