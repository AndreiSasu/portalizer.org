import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { VERSION } from 'app/app.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction: boolean;
  isNavbarCollapsed: boolean;
  languages: any[];
  swaggerEnabled: boolean;
  registrationEnabled: boolean;
  insightsEnabled: boolean;
  modalRef: NgbModalRef;
  version: string;
  account: Account;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router
  ) {
    this.version = VERSION ? 'v' + VERSION : '';
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {
    this.profileService.getProfileInfo().then(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
      this.registrationEnabled = profileInfo.registrationEnabled;
      this.insightsEnabled = profileInfo.insightsEnabled;
    });

    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl() {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
  }
}
