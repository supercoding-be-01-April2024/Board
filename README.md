# Post Board 프로젝트
## Summary

게시글과 댓글을 작성할 수 있는 게시판 프로젝트입니다. JAVA 17을 사용하여 개발되었습니다. JWT 토큰을 사용하여 회원가입, 로그인, 로그아웃 할 수 있습니다. 로그인 후에는 발급된 토큰으로 게시글 및 댓글을 작성, 수정, 삭제할 수 있습니다. 게시글 및 댓글을 찾아오는 것은 로그인 하지 않아도 가능합니다.

DB로는 MariaDB를 사용하였습니다. localDB에서 작동합니다.

## ERD
<img width="860" alt="Screenshot 2024-04-02 at 13 59 44" src="https://github.com/soheeparklee/sc_project01_April2024_verSoh/assets/97790983/485cd5cf-35dd-405a-8feb-c507ce47294b">


## 환경변수
이 프로젝트를 실행하기 위해서는 다음과 같은 환경변수들을 .yaml file에 추가하여야 합니다. 

`DATABASE_USERNAME` local database username

`DATABASE_PASSWORD` local database password

`JWT_SECRET_KEY` jwt password 



## API 명세서


#### Signup

```
  POST /auth/sign-up
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email` | `String` | **Required**. email |
| `password` | `String` | **Required**. password |
| `name` | `String` | **Required**. name |
| `phoneNumber` | `String` | **Required**. phoneNumber |


#### Login

```
  POST /auth/login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email` | `String` | **Required**. email |
| `password` | `String` | **Required**. password |


#### Logout

```
  POST /auth/logout
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `CustomUserDetails` | `token` | **Required**. Your JWT token |


### Comment API

#### Create Comment

```
  POST /comment/create?post-id
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `comment`      | `String` | **Required**. comment |


#### Get All Comment

```
  GET /comment/get/{postId}

```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `postId` | `@PathVariable Integer` | **Required**. post-Id |

#### Get Comment by keyword

```
  GET /comment/get/{keyword}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `keyword`      | `@PathVariable String` | **Required**. keyword |

#### Get Comment by User

```
  GET /comment/get/mycomment
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token |


#### Delete  Comments

```
  DELETE /comment/deletes
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `post-id`      | `@RequestParam Integer` | **Required**. post-id |
| `comment-id`      | `@RequestParam List<Integer>` | **Required**. comment-ids |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token |

#### Delete  Comment

```
  DELETE /comment/delete
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `post-id`      | `@RequestParam Integer` | **Required**. post-id |
| `comment-id`      | `@RequestParam Integer` | **Required**. comment-ids |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token |

#### Update  Comment

```
  PUT /comment/update{commentId}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `comment-id`      | `@RequestParam Integer` | **Required**. comment-ids |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token |

### API 명세서

#### Post API

#### Create post

```
  POST /posts/create
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `title` | `string` | **Required**. title |
| `content` | `string` | **Required**. content |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token 

#### Find post by email

```
  GET /posts/find/{email}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` | **Required**. email |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token 

#### Find everyposts

```
  GET /posts/findAll
```


#### Modify post

```
  POST /posts/modify/{post_id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Required**. title |
| `content`      | `string` | **Required**. content |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token 

#### Delete post

```
  DEL /posts/delete
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token |

#### Likes post

```
  GET /post/likes/{post_id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `post`      | `@RequestParam Integer` | **Required**. post_id |
| `CustomUserDetails`      | `@AuthenticationPrincipal` | **Required**. Token |

#### Find post details

```
  GET /post/find/id/{post_id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `post`      | `@RequestParam Integer` | **Required**. post_id |



## 실행결과 스크린샷

### USER API

#### Signup
<img width="555" alt="Screenshot 2024-04-05 at 17 38 21" src="https://github.com/soheeparklee/sc_project01_April2024_verSoh/assets/97790983/adaab762-8d0b-465c-89c4-742ab44b72e4">

#### Login
<img width="540" alt="Screenshot 2024-04-05 at 17 38 57" src="https://github.com/soheeparklee/sc_project01_April2024_verSoh/assets/97790983/6b31c9ae-779f-4329-8815-166f82628fd1">

#### Logout
<img width="553" alt="Screenshot 2024-04-05 at 17 39 23" src="https://github.com/soheeparklee/sc_project01_April2024_verSoh/assets/97790983/63b4d5f5-2327-4a6f-aea7-74aea0e9a6c6">

### Comment API

#### Create Comment
![댓글 생성](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/156086602/69b6e9e4-1548-41bd-b0cd-4afceb0a770c)


#### Get All Comment
![post에 해당하는 댓글 조회](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/156086602/7f4195f0-cf4c-49f3-81c0-c213d50ba7b2)


#### Get Comment by keyword
![키워드로 댓글 조회](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/156086602/59933a0a-df5b-4649-aa92-6ad1c3f33d7e)


#### Get Comment by User
![내가 쓴 댓글만 조회](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/156086602/864de5fb-b90b-4f72-be1c-ddc2d3cf8dac)


#### Delete  Comments
![댓글 리스트로 삭제](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/156086602/238cbed9-6c19-4222-9a84-92912ed7e7e3)


#### Delete  Comment
![댓글 1개씩 삭제](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/156086602/7acac772-0a61-4cdf-a943-48fc3776462a)


#### Update  Comment
![댓글 수정](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/156086602/b3669a5e-b2d4-4924-a7d1-9b021be7f470)

### POST API

#### Create post
![게시글생성](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/155543391/9bc318a8-7acd-4848-ac29-96856f426cba)

#### Find post by email
![이메일로 게시글 조회](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/155543391/67b14121-7ac3-49a5-b473-4da9be751686)

#### Find everyposts
![전체게시글 조회](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/155543391/f88777c3-007a-4bd2-a290-e1123b91b3dc)

#### Delete post
![게시글 삭제](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/155543391/1a52b8dc-547f-49fa-b2e6-0d72c6e3ca35)

#### Modify post
![게시글 수정](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/155543391/2fb5f095-8a17-4a91-8f69-66666c6271c5)

#### likes post
![게시글 좋아요](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/155543391/b7556ff6-555a-4e71-8d7c-e8185132b630)

#### Find post details
![게시글 상세조회](https://github.com/supercoding-be-01-April2024/project_post-board_April2024/assets/155543391/9f820957-1338-43aa-9041-3a3f718cb96a)

## 코드 리뷰 및 피드백
#### Post
- post를 find, findAll 또는 modify했을 때 postResponseDto를 만들어 찾아진 post가 user에게 보여야 할 것 같습니다. 현재는 “게시글 찾기 성공”메세지만 보여집니다.
- post를 한 개만 find했을 때, post에 딸린 comment도 보일 수 있도록 comment도 받아와 List로 보여주면 어떨까요?
#### Comment
- 날짜(createdAt)에 T가 중간에 들어가 있으므로 형식 지정하는건 어떨까요?
- 여러개의 comment를 delete했을 때, 어떤 comment는 삭제되고 어떤 comment는 삭제되지 않았는지 user에게 보여주면 user입장에서 댓글 삭제 현황을 파악하기 좋을 것 같습니다.

## 🔴 Trouble Shooting

####  🔴 내가 작성한 post, comment가 아닌데 로그인 했다는 이유만으로 수정, 삭제 가능
🟡 기존 코드
```java
    public ResponseDTO updatePost(CustomUserDetails customUserDetails, Integer postId, PostRequest postRequest) {
        User user= userJpa.findByEmailFetchJoin(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("이메일" + customUserDetails.getEmail() + "을 가진 유저를 찾지 못했습니다."));
        Post post= postJpa.findById(postId)
                .orElseThrow(()-> new NotFoundException("아이디 "+ postId +"에 해당하는 게시글이 없습니다."));

            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            Post updatePost = postJpa.save(post);

            PostDetailResponse postDetailResponse = new PostDetailResponse(
                    updatePost.getPostId(),
                    updatePost.getTitle(),
                    updatePost.getName(),
                    updatePost.getContent());

            return new ResponseDTO(HttpStatus.OK.value(), "Post updated successfully", postDetailResponse);
    }
```

🟢 해결 방법
- customUserDetails에서 name을 받아온다.
- post에서 name을 받아온다.
- if문을 사용하여 userName과 post의 name이 일치하면 post의 수정 또는 삭제를 가능하게 한다.
- 불일치한다면,  NotSameUserException을 던진다.

```java
    public ResponseDTO updatePost(CustomUserDetails customUserDetails, Integer postId, PostRequest postRequest) {
        User user= userJpa.findByEmailFetchJoin(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("이메일" + customUserDetails.getEmail() + "을 가진 유저를 찾지 못했습니다."));
        Post post= postJpa.findById(postId)
                .orElseThrow(()-> new NotFoundException("아이디 "+ postId +"에 해당하는 게시글이 없습니다."));

        String nameUser= user.getName();
        String namePost= post.getName();
        if(nameUser.equals(namePost)){
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            Post updatePost = postJpa.save(post);

            PostDetailResponse postDetailResponse = new PostDetailResponse(
                    updatePost.getPostId(),
                    updatePost.getTitle(),
                    updatePost.getName(),
                    updatePost.getContent());

            return new ResponseDTO(HttpStatus.OK.value(), "Post updated successfully", postDetailResponse);
        } else{
            throw new NotSameUserException("Post update fail. 작성자가 아닙니다.");
        }
    }
```

####  🔴 게시판 주인일 때 해당 게시판의 Comment들이 아닌 자신의 다른 게시판 Comment까지 삭제 가능한 문제
🟡 기존 코드
```java
public ResponseDTO deleteResult(Integer postId, Integer commentId, CustomUserDetails customUserDetails) {
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        Post post =postJpa.findById(postId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("Post Id : "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<Comment> comments = commentJpa.findAllByPost(post);
        if (comments.isEmpty()){
            throw new NotFoundException("해당 게시판에 삭제할 댓글이 없습니다.");
        }
        List<Comment> commentsByUser = commentJpa.findAllByUser(user);
        Comment comment = commentJpa.findById(commentId)
                .orElseThrow(()->new NotFoundException("Comment Id : "+commentId+"에 해당하는 comment가 존재하지 않습니다."));

        if (!comments.contains(comment)){
            throw new NotFoundException("해당 게시판 : "+ postId +"에 댓글이 아닙니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String createAt =now.format(dtf);
        try {
            if (post.getUser().equals(user)) {
                commentJpa.deleteById(commentId);
                return new ResponseDTO(HttpStatus.OK.value(),"Deletion of comment successful. "+ createAt);
            } else if (commentsByUser.contains(comment) && comments.contains(comment)) {
                commentJpa.deleteById(commentId);
                return new ResponseDTO(HttpStatus.OK.value(),"Deletion of comment successful. "+ createAt);
            } else return new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Failed to delete comment.");
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Failed to delete comment.");
        }
```

🟢 해결 방법
- post.getUser().equals(user) 해당했을 때만 아니라 post로 얻은 comments에도 포함되어 있는지를( comments.contains(comment) )추가한다. 
```java
public ResponseDTO deleteResult(Integer postId, Integer commentId, CustomUserDetails customUserDetails) {
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        Post post =postJpa.findById(postId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("Post Id : "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<Comment> comments = commentJpa.findAllByPost(post);
        if (comments.isEmpty()){
            throw new NotFoundException("해당 게시판에 삭제할 댓글이 없습니다.");
        }
        List<Comment> commentsByUser = commentJpa.findAllByUser(user);
        Comment comment = commentJpa.findById(commentId)
                .orElseThrow(()->new NotFoundException("Comment Id : "+commentId+"에 해당하는 comment가 존재하지 않습니다."));

        if (!comments.contains(comment)){
            throw new NotFoundException("해당 게시판 : "+ postId +"에 댓글이 아닙니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String createAt =now.format(dtf);
        try {
            if (post.getUser().equals(user) && comments.contains(comment)) {//post로 얻은 comments에도 포함되어 있는지 조건 추가 
                commentJpa.deleteById(commentId);
                return new ResponseDTO(HttpStatus.OK.value(),"Deletion of comment successful. "+ createAt);
            } else if (commentsByUser.contains(comment) && comments.contains(comment)) {
                commentJpa.deleteById(commentId);
                return new ResponseDTO(HttpStatus.OK.value(),"Deletion of comment successful. "+ createAt);
            } else return new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Failed to delete comment.");
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Failed to delete comment.");
        }
```

####  🔴 delete comment를 위해 여러개의 commentId를 받았을 때, 내가 쓴 댓글만 지우고 나머지는 그대로 두기
🟡 기존 코드
```java
public ResponseDTO deleteResults(Integer postId,List<Integer> commentIds, CustomUserDetails customUserDetails) {
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        Post post =postJpa.findById(postId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("Post Id : "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<Comment> commentsByPost = commentJpa.findAllByPost(post);
        if (commentsByPost.isEmpty()){
            throw new NotFoundException("해당 게시글에 댓글을 남기시지 않았습니다.");
        }
            List<Integer> postCommentId = commentsByPost.stream().map(Comment::getCommentId).toList();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String createAt =now.format(dtf);
            if (post.getUser().equals(user)){
                commentIds.retainAll(postCommentId);
                if (commentIds.isEmpty()){
                    throw new com.example.github.service.exceptions.NotFoundException("해당 게시판의 댓글이 아니거나 본인 댓글이 아니어서 삭제할 댓글이 없습니다.");
                }
                commentJpa.deleteAllById(commentIds);
                    return new ResponseDTO(HttpStatus.OK.value(),"Comments ("+ commentIds+") have been deleted."+ createAt);

            }else {
                List<Comment> comments = commentJpa.findAllByUser(user);
                List<Integer> commentIdList = comments.stream().map(Comment::getCommentId).collect(Collectors.toList());
                if (commentIds.isEmpty()){
                    throw new com.example.github.service.exceptions.NotFoundException("해당 게시판의 댓글이 아니거나 본인 댓글이 아니어서 삭제할 댓글이 없습니다.");
                }
                commentJpa.deleteAllById(commentIds);
                return new ResponseDTO(HttpStatus.OK.value(),"Comments ("+ commentIds+") have been deleted. "+ createAt);
            }
    }
```

🟢 해결 방법
- retainAll을 사용하여 내가 요청한 commentIds에서  내가 쓴 댓글 리스트(commentIdList)와 중복되는 comment-id만 commentids에 남겨둔다.(조회한 리스트에 내가 쓴 댓글이 아닌 게 포함되어 있어도 내가 쓴 댓글 리스트와 비교하여 내가 쓴 댓글만 남겨둘 수 있음)
- 중복된 값만 남은 commentids에서 retainAll을 한 번 더 이용해 해당 Post에 있는 postCommentId와 중복된 값만 commentids에 남겨둔다(내가 쓴 댓글만 남은 commentids에서 retainAll을 사용해 postCommentid와 동일한 것만 남아 해당 post에 내가 쓴 댓글만 삭제할 수 있도록 만들었다)
```java
public ResponseDTO deleteResults(Integer postId,List<Integer> commentIds, CustomUserDetails customUserDetails) {
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        Post post =postJpa.findById(postId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("Post Id : "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<Comment> commentsByPost = commentJpa.findAllByPost(post);
        if (commentsByPost.isEmpty()){
            throw new NotFoundException("해당 게시글에 댓글을 남기시지 않았습니다.");
        }
            List<Integer> postCommentId = commentsByPost.stream().map(Comment::getCommentId).toList();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String createAt =now.format(dtf);
            if (post.getUser().equals(user)){
                commentIds.retainAll(postCommentId);
                if (commentIds.isEmpty()){
                    throw new com.example.github.service.exceptions.NotFoundException("해당 게시판의 댓글이 아니거나 본인 댓글이 아니어서 삭제할 댓글이 없습니다.");
                }
                commentJpa.deleteAllById(commentIds);
                    return new ResponseDTO(HttpStatus.OK.value(),"Comments ("+ commentIds+") have been deleted."+ createAt);

            }else {
                List<Comment> comments = commentJpa.findAllByUser(user);
                List<Integer> commentIdList = comments.stream().map(Comment::getCommentId).collect(Collectors.toList());
                commentIds.retainAll(commentIdList);  //retainAll을 사용하여 내가 요청한 commentIds에서  내가 쓴 댓글 리스트(commentIdList)와 중복되는 comment-id만 commentids에 남겨둔다.(
                commentIds.retainAll(postCommentId);  //중복된 값만 남은 commentids에서 retainAll을 한 번 더 이용해 해당 Post에 있는 postCommentId와 중복된 값만 commentids에 남겨둔다
                if (commentIds.isEmpty()){
                    throw new com.example.github.service.exceptions.NotFoundException("해당 게시판의 댓글이 아니거나 본인 댓글이 아니어서 삭제할 댓글이 없습니다.");
                }
                commentJpa.deleteAllById(commentIds);
                return new ResponseDTO(HttpStatus.OK.value(),"Comments ("+ commentIds+") have been deleted. "+ createAt);
            }
    }
```

#### ❌ Cannot automatically merge
먼저 develop에 commit branch를 머지하였고 이후 post branch를 머지하려고 했더니 Cannot automatically merge에러가 떴다. 이럴 때 해결방법은 다음과 같다. 

1. 먼저 post branch를 개발한 사람의 컴퓨터에서 develop으로 체크아웃한다.  git checkout devleop
2. develop에서 pull을 통해 바뀐 develop의 내용들을 가져온다. git pull orgin develop
3. 다시 post branch로 체크아웃한다. git checkout post
4. 그러면 다시 develop으로 merge했을 때 오류없이 머지가 가능하다. git merge develop
5. 문제가 생기면 코드 수정
6. 수정했으면 git add . 
7. git commit -m “” 
8. git push
9. pull request


## 🔵 발전시킬 수 있는 부분
#### token을 Redis에 추가
현재는 login할 때마다 새로운 token을 발급하고 logout하면 token을 blacklist에 추가합니다. 따라서 login할 때 사용한 token을 따로 저장해두거나 logout한 token 또는 withdraw(회원탈퇴)한 token이 달리 저장되지 않습니다. 단지 메모리에 저장되기 때문에 앱을 재실행하면 token이 날아가게 됩니다. 이를 개선하기 위해 Redis를 implement할 수 있을 것으로 생각됩니다. 

#### social login 추가
token을 저장하는 server를 우리가 만드는 것보다는 구글이나 네이버같은 회사의 서버를 빌리는 것이 더욱 안전하고 간편할 것이라 생각됩니다. 이를 위해 social login을 추가할 수 있습니다. 

#### 댓글 비공개 추가
자신의 댓글 내용이 게시판 주인이 아닌 다른 사람에게 보여지는 것이 싫어하는 분들에 있어서 Comment 생성할 때 비공개 설정 여부를 추가하면 댓글을 사용하는 부분에 있어서 더 많은 사람들이 이용할 수 있도록 개선이 될 거 같습니다.

#### 공용DB 사용
이 프로젝트는 데이터베이스로 MariaDB를 사용하며 각자의 local database에서 작동합니다. 공용DB를 사용함으로써 모두가 같은 데이터를 공유할 수 있습니다. 
