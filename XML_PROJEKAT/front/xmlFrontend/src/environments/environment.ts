// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  base_url: 'http://host.docker.internal:8080',
  auth_url: 'http://host.docker.internal:8090/auth',
  login_url: 'http://host.docker.internal:8080/login',
  attack_url: 'http://host.docker.internal:8090/attack',
  profile_url: 'http://host.docker.internal:8140/profile',
  image_url: 'http://host.docker.internal:8120/image',
  post_url: 'http://host.docker.internal:8130/post',
  story_url: 'http://host.docker.internal:8130/story',
  verificationRequest_url: 'http://host.docker.internal:8140/verificationRequest',
  followRequest_url: 'http://host.docker.internal:8110/followRequest',
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
