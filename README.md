project :BASIC to java interpreter

project members and roles:

Dachi Tkemaladze
project manager
Wrote incoming code's fetching logic

Elene Grigalashvili
wrote evaluation of expression

Marta Lomidze
wrote variable assignment and print functions

Nana  Giorgadze
wrote Basic code given algorithms 

Marta + Nana
wrote while loop + if/else logic 
____________________________________________________________________________________________________________

Work flow
code comes ,it is split by line by line and then packed into String array 
every line which is empty is discarded so is empty space before first words 
then there is while loop which goes through array and do instructions 1 by 1
it sends to proper function by its instruction type
Dim-variable assignment
While- while loop
if- conditionals
print-output
if logic 
it gets condition and returns number from where code should go on executing
While logic 
gets condition and returns number like if but when it reaches Wend command it sends back to condition
and does it until condition is not satisfied if it was not satisfied in first cycle it jumps to wend's+1 line
___________________________________________________________________________________________________________

User guide

variable assignment:
Dim varName As Integer = varValue
Dim varName As Integer 
both is accepted 1st gives value since its provided second gives default int value which is zero

print:
print varName
u can only print varriables if u want to print something u can assign it as a variable and then print it 
for example

print 5 -is not allowed

Dim temp As Integer = 5
print temp

if/else:
every if should has it's "End if"

If 5>3 
	print var
End if
or
If varName1 = varNme2
	print var
End if
also condition should not contain expression to evaluate u should calculate it in advance

While: everything i said for if it goes for while as well instead of End if it has Wend

everything should be distanced with " "

for example:

x = x + 5 -correct
x=x+5-incorrect
if 5<10-incorrect
if 5 < 10-correct
________________________________________________________________________________________________________________________________________________________________
even though Marta pushed and merged code she is not appeared in contributors list. dont know why

also test cases that are given was written before we started coding and needs little change but every one of them is writable with given interpreter 
for example this is prime number check:


Dim num as Integer = 29
Dim isPrime as Integer = 1
Dim i as Integer = 2
Dim temp as Integer
Dim temp2 as Integer 

temp = num / 2
                    
While i < temp
     temp2 = num % i                    
     If temp2 = 0 Then
           isPrime = 0       
     End If
     i = i + 1
Wend                
print isPrime







