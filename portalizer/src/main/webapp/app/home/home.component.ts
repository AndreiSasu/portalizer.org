import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { faInfoCircle } from '@fortawesome/free-solid-svg-icons';
import { faGithub, faDocker } from '@fortawesome/free-brands-svg-icons';
import { colorSets } from '@swimlane/ngx-charts';

import { InsightsService } from 'app/insights/insights.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account;
  authSubscription: Subscription;
  modalRef: NgbModalRef;
  faInfoCircle = faInfoCircle;
  faDocker = faDocker;
  faGithub = faGithub;

  counters: any[];
  colorSets: any;

  colorScheme: any;
  cardColor = '#FFFFFF';

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private insightsService: InsightsService,
    private eventManager: JhiEventManager,
    private router: Router
  ) {
    Object.assign(this, { colorSets });
    this.colorScheme = this.colorSets.find(s => s.name === 'vivid');
  }

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();

    this.insightsService.getCounters().then(counters => {
      this.counters = counters;
    });
  }

  registerAuthenticationSuccess() {
    this.authSubscription = this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.eventManager.destroy(this.authSubscription);
    }
  }

  onSelect(event: any) {
    this.router.navigateByUrl('retro/boards');
  }
}
