/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

export interface MessageResponse {
  content?: string;
  createdAt?: string;
  id?: number;
  media?: string;
  recipientId?: string;
  senderId?: string;
  state?: 'SENT' | 'SEEN';
  type?: 'TEXT' | 'IMAGE' | 'AUDIO' | 'VIDEO';
}
