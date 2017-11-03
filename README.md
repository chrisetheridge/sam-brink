# Sam Brink personal website

## Overview

Website for artist portfolio. 

Written in Clojurescript, compiled to to Javascript, hosted on Github pages.

## Building

Make sure you have [leiningen](https://leiningen.org/) installed.

Autoloading and local serving is provided by [figwheel](https://github.com/bhauman/lein-figwheel).

```bash
git clone git@github.com:chris-etheridge/sam-brink
cd sam-brink

# for dev - autoload compiled js and css using 
lein figwheel dev

# for production / minified build
# outputs to resources/public/js/
lein cljsbuild once min
```

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
