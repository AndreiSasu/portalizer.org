import { Route } from '@angular/router';

import { JhiTrackerComponent } from './tracker.component';

export const trackerRoute: Route = {
  path: 'tracker',
  component: JhiTrackerComponent,
  data: {
    pageTitle: 'Real-time user activities'
  }
};
