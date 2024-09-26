# Language-Processor
## Overview
This project is a Language Processor implemented in Java, designed to read, analyze, and interpret a custom programming language similar to Javascript. It performs lexical, syntactical, and semantic analysis to check if the code doesn't have lexical, sintactical or semantic errors.

The core functionalities of this language processor include:

Lexical Analysis (Tokenization): Breaking the input source code into meaningful symbols or tokens.
Syntactic Analysis (Parsing): Checking the token stream against the defined grammar rules.
Semantic Analysis: Ensuring the logical correctness of the parsed code.

## Requirements
Java 8 or later.

## Structure
The analyzer is executed through the "Analizador.java" file which in turn executes the code in the other files. The "ALex" directory contains all code related to the lexical analyzer and the "ASin" directory contains all code from the sintactical and semantic analyzer. The "Error.java" file contains all the possible errors that the program can throw. Lastly, the "TS" directory contains the code for the table of symbols. 
