#!/bin/bash

# Run from the directory with all of the outputted folders
# TSV files are in each individual folder named as part-00000
cat ./*/part-00000 >merged.tsv


