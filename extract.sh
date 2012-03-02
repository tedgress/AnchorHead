#! /bin/bash
die () {
    echo >&2 "$@"
    exit 1
}

[ "$#" -eq 2 ] || die "2 arguments required, input and output file"
perl -ne '/message>(.*)</ && print "$1\n"' $1 > $2
