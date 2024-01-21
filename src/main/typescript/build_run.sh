#!/bin/bash

tsc
npx webpack --mode production
cp -i ./dist/bundle.min.js ../resources/static/static/js/etwig/recurrent.min.js
