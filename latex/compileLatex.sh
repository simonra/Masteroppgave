#!/bin/bash
TEX_FILE_TO_COMPILE="main.tex"
BIBTEX_AUX_FILE="main.aux"
pdflatex $TEX_FILE_TO_COMPILE && bibtex $BIBTEX_AUX_FILE && pdflatex $TEX_FILE_TO_COMPILE && pdflatex $TEX_FILE_TO_COMPILE
