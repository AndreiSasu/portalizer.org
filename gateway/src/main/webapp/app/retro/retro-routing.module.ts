import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BoardSummaryComponent } from './board-summary/board-summary.component';

const routes: Routes = [{ path: 'boards', component: BoardSummaryComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RetroRoutingModule {}
