# EBI_00971
AccessionNumbers
The application accepts an unordered list of accession numbers and converts them to ordered list where same prefix consecutive numbers are collapsed into a range.
The following assumptions are made - 
1) All the inputs are converted to upper-case, before running the alogrithm.
2) Any white space is ignored
3) If the input is invalid, that value is ignored in the final output.
  a) Numbers not in the form LL...LNN...NN are considered invalid.
4) Only cli usage is supported.
5) For command line usage, type the following to execute -
  a) java -jar EBI_00971.jar
  
Example - 

C:\Users\prabhakj\workspace\EBI_00971\src\main\java\EBI_00971\EBI_00971>java -jar C:\Users\prabhakj\Desktop\EBI_00971.jar <Press Enter>
Enter Accession Numbers
A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115,ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003,DRR2110012, SRR211001, SRR211002 <Press Enter>
[A00000, A0001, DRR2110012, ERR000111 - ERR000113, ERR000115 - ERR000116, ERR100114, ERR200000001 - ERR200000003, SRR211001 - SRR211002]
