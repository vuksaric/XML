// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  base_url: 'http://localhost',
  auth_url: 'http://localhost/auth',
  attack_url: 'http://localhost/auth/attack',
  profile_url: 'http://localhost/profile',
  image_url: 'http://localhost/picture-video',
  post_url: 'http://localhost/post',
  story_url: 'http://localhost/post/story',
  verificationRequest_url: 'http://localhost/profile/verificationRequest',
  followRequest_url: 'http://localhost/notification/followRequest',
  reportRequest_url: 'http://localhost/notification/reportRequest',
  registrationRequest_url: 'http://localhost/auth/registrationRequest',
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
