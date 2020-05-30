import { Component } from '@angular/core';
import { faLinkedin, faGithub, faAngular, faAws, faJava, faDocker } from '@fortawesome/free-brands-svg-icons';
import { ProfileService } from '../profiles/profile.service';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['footer.scss']
})
export class FooterComponent {
  faGithub = faGithub;
  faLinkedin = faLinkedin;
  faAngular = faAngular;
  faAws = faAws;
  faJava = faJava;
  faDocker = faDocker;
  isDemo = false;
  constructor(private profileService: ProfileService) {
    this.profileService.getProfileInfo().then(profile => {
      this.isDemo = profile.activeProfiles.includes('demo');
    });
  }
}
