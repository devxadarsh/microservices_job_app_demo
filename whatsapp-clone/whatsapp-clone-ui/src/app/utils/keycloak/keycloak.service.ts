import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root',
})
export class KeycloakService {
  private _keycloak: Keycloak | undefined;

  constructor(private router: Router) {}

  get keycloak() {
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:9090',
        realm: 'whatsapp-clone',
        clientId: 'whatsapp-clone-app',
      });
    }
    return this._keycloak;
  }

  async init() {
    const authenticated = await this.keycloak.init({
      onLoad: 'login-required',
    });
  }

  async login() {
    await this.keycloak.login();
  }

  get userId() {
    return this.keycloak?.tokenParsed?.sub as string;
  }

  get isTokenValid() {
    return !this.keycloak.isTokenExpired();
  }

  get fullName() {
    return this.keycloak.tokenParsed?.['name']?.sub as string;
  }

  logOut() {
    return this.keycloak.login({ redirectUri: 'https://localhost:4200' });
  }

  accountManagement() {
    return this.keycloak.accountManagement();
  }
}
