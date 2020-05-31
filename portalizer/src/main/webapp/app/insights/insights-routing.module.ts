import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InsightsComponent } from './insights/insights.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

const routes: Routes = [
  {
    path: '',
    component: InsightsComponent,
    canActivate: [UserRouteAccessService],
    data: {
      authorities: [],
      pageTitle: 'Board Insights'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InsightsRoutingModule {}
