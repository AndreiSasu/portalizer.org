import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BoardSummaryComponent } from './board-summary/board-summary.component';
import { BoardDetailsComponent } from './board-details/board-details.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { InsightsComponent } from './insights/insights.component';

const routes: Routes = [
  {
    path: 'boards',
    component: BoardSummaryComponent,
    canActivate: [UserRouteAccessService],
    data: {
      authorities: [],
      pageTitle: 'Retrospective Boards'
    }
  },
  {
    path: 'boards/:id',
    component: BoardDetailsComponent,
    canActivate: [UserRouteAccessService],
    data: {
      authorities: [],
      pageTitle: 'Board Details'
    }
  },
  {
    path: 'insights',
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
export class RetroRoutingModule {}
