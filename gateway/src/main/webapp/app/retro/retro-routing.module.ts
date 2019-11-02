import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BoardSummaryComponent } from './board-summary/board-summary.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

const routes: Routes = [
  {
    path: 'boards',
    component: BoardSummaryComponent,
    canActivate: [UserRouteAccessService],
    data: {
      authorities: [],
      pageTitle: 'Retrospective Boards'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RetroRoutingModule {}
