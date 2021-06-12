// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  base_url: 'http://localhost:8080',
  auth_url: 'http://localhost:8090/auth',
  login_url: 'http://localhost:8080/login',
  attack_url: 'http://localhost:8090/attack',
  profile_url: 'http://localhost:8140/profile',
  image_url: 'http://localhost:8120/image',
  post_url: 'http://localhost:8130/post',
  story_url: 'http://localhost:8130/story',
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
