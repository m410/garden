Sass module
=================================

Add's sass compilation to garden projects. 
 
It uses node package manager to create a node_modules subdirectory in src/sass that can be used to reference
external stylesheets.

Example configuration

    modules(org.m410.garden:garden-sass:0.1):
      source: src/sass/style.scss
      output: _/css/style.css
      compress: false
      dependencies:
      - {name: bootstrap, version: 4.0.0-alpha.4}