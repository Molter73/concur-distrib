#!/usr/bin/env python3

import argparse
import random
import sys

SEVERITIES = [
    "WARN",
    "SEVERE",
    "INFO",
]


def emit_log_line():
    return f'wallet-rest-api[{random.choice(SEVERITIES)}]: Lorem Ipsum.'


def main(count, output):
    for _ in range(count):
        output.write(f'{emit_log_line()}\n')


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-n', '--count', type=int, default=100,
                        help='Cantidad de líneas a emitir')
    parser.add_argument('-o', '--output', type=str, default=None,
                        help='Archivo de salida. Default: stdout')

    args = parser.parse_args()

    output = args.output
    output = sys.stdout if args.output is None else open(output, 'w')
    main(args.count, output)
