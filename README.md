# Case Study - signalseek API

<p align="center">
    <img src="/screenshots/signalseekAPI.png" alt="Project Management" width="800" height="700">
</p>


<ul style="list-style-type:square">
  <li>
    Signalseek project will asist when you want to seek someone as group. This repository include restful api backend that signal project's. There are 5 entites for backend. AdminUserEntity, UserEntity, GeoLocationEntity,ApplicationLogEntity,RoomEntity. Project will save logs on server and database. Project used springboot framework, postgresql in docker container. Searching and analysis beign on web interface. Also There is admin panel on web interface.
  </li>
  <li>
    <h1>How To Start Exploration</h1>
    We need to create a room on web interface. After that we can start searching on mobile app. But we need to take roomKey on web interface. We can create different groups from web interface. Furthermore users will add bookmarks on web interface map. In this way we can look around whole bookmarks on web.
  </li>
  <p>
  <li>
    <b>User Authentication:</b>
    <ul>
      <li>
        <b>User Registration:</b> Mobile app will send a POST request included roomkey for user. After that user will get a bearer token.
      </li>
      <li>
        <b>User Move:</b> Mobile app will send request included a bearer token. 
      </li>
      <li>
        <b>User Finds:</b> Whole find endpoints needed a roomkey authorization. Thence other users can't access information between each other.
      </li>
      <li><b>User Delete:</b> This endpoint needs <i>ROLE_ADMIN</i> and <i>ROLE_ROOM</i> authorization.</li>
      <li><b>User Update:</b> This endpoint needs <i>ROLE_ADMIN</i> and <i>ROLE_ROOM</i> authorization.</li>
      <li><b>User Test:</b> Everybody can access this endpoint. It's just a service working test query.</li>
    </ul>
  </li>
  <li>
    <b>Room Authentication:</b>
    <ul>
      <li>
        <b>Create Room:</b> Web interface sends POST request. After that server gives response that included RoomKey and a Bearer token.
      </li>
      <li>
        <b>Delete Room:</b> Web interface sends DELETE post that included a bearer token authenticated <i>ROLE_ROOM</i> and <i>ROLE_ADMIN</i> response includes a Bearer token.
      </li>
      <li>
        <b>Find All Room:</b> This endpoint about admin panel. So it needs <i>ROLE_ADMIN</i> authorization.
      </li>
      <li>
        <b>Find Room:</b> Web interface needs to search users in self room. Interface sends GET HTTP request. Also request includes a Bearer token that included roomkey. So everybody can access self room information.
      </li>
    </ul>
  </li>
  <li>
    <b>Admin Authentication:</b>
    <ul>
      <li>
        <b>Admin Login:</b> Password is encrypted as One-Timed. When admin logged in successfully, server sends response back that includes a Bearer token.
      </li>
      <li>
        <b>Admin Register:</b> Only those with the <i>ROLE_ADMIN</i> role on the server side have access to this endpoint.
      </li>
    </ul>
  </li>
  <li>
    <b>Application Log Authentication:</b>
    <ul>
      <li>
        <b>Create, find, delete and update:</b> Only those with the <i>ADMIN</i> role on the server side have access to this endpoint.
      </li>
    </ul>
  </li>
  <li>
    <b>Security:</b>
    <ul>
      <li>
        <b>User Roles:</b> <i>ROLE_ADMIN</i> , <i>ROLE_ROOM</i> and <i>ROLE_USER</i> roles implemented using Spring Security.
      </li>
      <li><b>JWT Authentication:</b> Secure endpoints with JSON Web Tokens.</li>
    </ul>
  </li>
  <li>
    <b>Logging:</b>
    <ul>
      <li>
        <b>Custom Logging Aspect:</b> slf4j Logs details of REST controller method calls and
        exceptions, including HTTP request and response details.
      </li>
    </ul>
  </li>
</ul>


## Endpoint Guide

<table style="width:100%;">
    <tr>
        <th>Method</th>
        <th>Url</th>
        <th>Description</th>
        <th>Request Body</th>
        <th>Path Variable</th>
        <th>Response</th>
    </tr>
    <tr>
        <td>GET</td>
        <td>/user/status</td>
        <td>Service working test</td>
        <td></td>
        <td></td>
        <td>ResponseEntity&lt;OK&gt;</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/user/create</td>
        <td>User creating endpoint</td>
        <td>UserDto</td>
        <td></td>
        <td>ResponseEntity&lt;JwtResponse&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/user/all</td>
        <td>Gets all users</td>
        <td></td>
        <td></td>
        <td>ResponseEntity&lt;List&lt;UserDto&gt;&gt;</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/user/update</td>
        <td>Modify user with with userkey</td>
        <td>UserDto</td>
        <td>userKey</td>
        <td>ResponseEntity&lt;UserDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/user/find</td>
        <td>Gets user by ID</td>
        <td></td>
        <td>id</td>
        <td>ResponseEntity&lt;UserDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/user/find</td>
        <td>Get user by userKey</td>
        <td></td>
        <td>userKey</td>
        <td>ResponseEntity&lt;UserDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/user/find</td>
        <td>Get user by roomKey</td>
        <td></td>
        <td>roomKey</td>
        <td>ResponseEntity&lt;UserDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/user/find</td>
        <td>Get user by IpAddress</td>
        <td></td>
        <td>ipAddress</td>
        <td>ResponseEntity&lt;UserDto&gt;</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/user/deletebyuserkey</td>
        <td>Delete an User</td>
        <td></td>
        <td>userKey</td>
        <td>ResponseEntity&lt;UserDto&gt;</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/user/move</td>
        <td>Post geolocation included user</td>
        <td></td>
        <td>userKey,latitude,longitude</td>
        <td>ResponseEntity&lt;UserDto&gt;</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/room/create</td>
        <td>Create room</td>
        <td>RoomDto</td>
        <td></td>
        <td>ResponseEntity&lt;JwtResponse&gt;</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/room/delete</td>
        <td>Delete an user</td>
        <td></td>
        <td>roomKey</td>
        <td>ResponseEntity&lt;RoomDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/room/all</td>
        <td>Get all rooms</td>
        <td></td>
        <td></td>
        <td>ResponseEntity&lt;List&lt;RoomDto&gt;&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/room/find</td>
        <td>Find a user by roomKey</td>
        <td></td>
        <td>roomKey</td>
        <td>ResponseEntity&lt;RoomDto&gt;</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/admin/login</td>
        <td>Admin login</td>
        <td>AdminUserDto</td>
        <td></td>
        <td>ResponseEntity&lt;JwtResponse&gt;</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/admin/register</td>
        <td>Admin register</td>
        <td>AdminUserDto</td>
        <td></td>
        <td>ResponseEntity&lt;AdminUserDto&gt;</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/admin/log/create</td>
        <td>Create application log</td>
        <td>ApplicationLogDto</td>
        <td></td>
        <td>ResponseEntity&lt;ApplicationLogDto&gt;</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/admin/log/delete</td>
        <td>Delete a ApplicationLog</td>
        <td>ApplicationLogDto</td>
        <td></td>
        <td>ResponseEntity&lt;ApplicationLogDto&gt;</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/admin/log/update</td>
        <td>Admin login</td>
        <td>ApplicationLogDto</td>
        <td>id</td>
        <td>ResponseEntity&lt;ApplicationLogDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/admin/log/find</td>
        <td>Get an ApplicationLog</td>
        <td></td>
        <td>id</td>
        <td>ResponseEntity&lt;ApplicationLogDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/admin/log/find</td>
        <td>Get all ApplicationLog</td>
        <td></td>
        <td></td>
        <td>ResponseEntity&lt;List&lt;ApplicationLogDto&gt;&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/admin/log/find</td>
        <td>Get an ApplicationLog by loggernameClass</td>
        <td></td>
        <td>loggername</td>
        <td>ResponseEntity&lt;ApplicationLogDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/admin/log/find</td>
        <td>Get an ApplicationLog by LogType</td>
        <td></td>
        <td>LogType</td>
        <td>ResponseEntity&lt;ApplicationLogDto&gt;</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/admin/log/find</td>
        <td>Get an ApplicationLog by userId</td>
        <td></td>
        <td>userId</td>
        <td>ResponseEntity&lt;ApplicationLogDto&gt;</td>
    </tr>
</table>

## Technologies

<ul style="list-style-type:square">
<li> Java 24
</li>
<li> SpringBoot 3.4.4
</li>
<li> Restful API
</li>
<li> Open API (Swagger)
</li>
<li> Docker
</li>
<li> Docker - Compose
</li>
<li> Postman
</li>
<li> PostgreSQL
</li>
<li> Lombok
</li>
<li> jjwt
</li>
</ul>

## Postman

 Needed to import from postman_collection folder

### Open Api (Swagger)

```
https://signalseek.xyz/swagger-ui/index.html
```