# -*- coding: utf-8 -*-
"""
Created on Mon Oct  3 02:10:23 2011

@author: IxxI
@version: v1.0
"""
import sys, time
from optparse import OptionParser
from commands import Commands

def main(conffile=None):
    commands = Commands(conffile)

    commands.logger.info ('> Getting modified client source')
    if commands.checksources(0):
        commands.gathermd5s(0, True)
        commands.unpackmodifiedclasses(0)
    commands.logger.info ('> Getting modified server source')
    if commands.checksources(1):
        commands.gathermd5s(1, True)
        commands.unpackmodifiedclasses(1)

if __name__ == '__main__':
    parser = OptionParser(version='MCP %s' % Commands.MCPVersion)
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    (options, args) = parser.parse_args()
    main(options.config)
