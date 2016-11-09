Javascript module
=================================

This uses node package manage to download and manage javascript dependencies.

example configuration

    modules(org.m410.garden:garden-javascript:0.1):
      watch:
      - file: index.html
        compressed: false
        bundled: false
        dependencies:
        - {name: lodash, version: 4.16.4}
        - {name: "@angular/common", verion: 2.1.1}

This will create a node_modules directory inside `src/webapp` were js dependencies can be referenced.