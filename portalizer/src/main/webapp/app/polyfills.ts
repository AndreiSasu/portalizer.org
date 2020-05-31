import 'core-js/proposals/reflect-metadata';
import 'zone.js/dist/zone';
import '@angular/localize/init';
require('../manifest.webapp');
// TODO: Remove when this gets solved: https://github.com/valor-software/ng2-dragula/issues/849
(window as any).global = window;
