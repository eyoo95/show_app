# 공연 조회 JAVA Android Application  (뭐보러갈래) Overview
https://docs.google.com/presentation/d/1WwfQ5CU3-UyYyVl5f6w5fggurQ5EiNB2GDk96inoQos/edit?usp=sharing
- 공연 정보와 내 지역의 공연 정보를 확인 할 수 있습니다.
- 사용자 기반 협업 필터링을 활용한 추천 인공지능으로 사용자들의 활동 패턴을 확인하고 좋아 할 것 같은 공연을 추천하는 기능을 포함하고 있습니다.
- 공연 티켓 이미지를 업로드하면 이미지의 글자를 판독하여 해당 공연과 일치하는 공연 티켓인지 확인하는 인공지능 기능을 포함하고 있습니다.
- 사용자들끼리 소통하여 다른 유저들과 함께 공연을 볼 수 있게 하자는 취지의 파티 찾기 기능이 포함되어 있습니다

# Open API
- 공연전시KOPIS
- Google Maps
- 클로바 OCR

#  OpenSource Library
- ViewPager2
- retrofit2
- Glide
- firebase
- yoyo
- Commons IO

# App SiteMap
![readMe_SiteMap](https://user-images.githubusercontent.com/105832386/190550178-7716bb56-ba26-415c-9301-592576646acd.png)

# Important files
### **메인**
-  MainActivity : 앱 기본 구조가 4개의 프래그먼트를 메인으로 작동하기 때문에 메인 액티비티에서는 BottomNavigationView 를 이용해 프래그먼트의 화면 전환시켜주는 기능이 존재한다. 

### **홈 화면**
  - HomeFragment : 앱에 로그인하면 가장 먼저 만날 수 있는 화면으로 이미지 배너와 여러 공연 리스트로 이루어져 있다.
    - 검색기능 : 여러 조건을 설정하여 공연을 검색할 수 있다.
    - 내 취향 공연 보기 : 사용자 경험을 토대로 사용자가 좋아할만한 공연을 자동으로 추천해준다.
    - 각 공연을 눌러 공연 상세 정보를 볼 수 있는 액티비티로 이동할 수 있다.

### **내 주변 공연 찾기 (지도)**
  - MapFragment : Google Map API를 이용하였으며, 사용자의 GPS위치를 기반으로 주변 공연을 찾아준다.
    - 지도에 공연정보를 띄워주고 클릭 시 해당 공연 정보를 열람할 수 있다.

### **커뮤니티**
  - CommunityFragment : 파티 찾기 기능과 자유게시판, 유저 리뷰를 작성 열람 수정 삭제가 가능하다. 
    - 파티 찾기 : Firebase를 이용하여 채팅창 만들고, 이를 통해 유저들이 자유롭게 방을 만들고 소통할 수 있게 하였다.
    - 자유게시판 : 로그인된 유저라면 모두 자유롭게 글을 열람하고 자신의 글을 작성하고 수정, 삭제할 수 있다.
    - 리뷰게시판 : 유저가 공연을 보고 리뷰를 작성할 때, 티켓 사진을 올리면 클로바 OCR을 통해 티켓 속 텍스트를 추출하여 리뷰를 올리려는 공연과 맞는 티켓인지 판단하고 맞다면 티켓인증 마크와 함께 리뷰를 게시할 수 있다.  

### **내 정보**
  - MyPageFragment: 사용자가 활동한 기록과 유저 정보를 수정하고 회원탈퇴가 가능하다.

# 실제 구동 화면

<img src="https://user-images.githubusercontent.com/105832386/190554589-34740111-84ba-43e7-935c-3c49ea36772c.png" width="300" height="300"><img src="https://user-images.githubusercontent.com/105832386/190554594-c91215f0-8358-47a7-95b3-24e7b301a9f7.png" width="300" height="300"><img src="https://user-images.githubusercontent.com/105832386/190554596-680e9d29-4e1f-452f-bf71-5a7b6dd50c5a.png" width="300" height="300"><img src="https://user-images.githubusercontent.com/105832386/190554597-54a118a8-972b-4354-b06a-fbe98e127b4b.png" width="300" height="300"><img src="https://user-images.githubusercontent.com/105832386/190554599-60a95d1b-faad-4eaf-bc27-5817436f9c4f.png" width="300" height="300"><img src="https://user-images.githubusercontent.com/105832386/190554603-992aac1c-b601-48c5-ab1e-bd883be6ec81.png" width="300" height="300">


개발기술서 Google Slides 주소: https://docs.google.com/presentation/d/1BJ2cNw5j3P4FOtlDy8OxZ3UoWne1K10SR-14FXz_giI/edit?usp=sharing
화면 UI 기획서 Oven 주소: https://ovenapp.io/view/mPoCI4eiLuu7fKlOjkeaHgkUKuUto8Qr/DRB62
테이블정의서 ErdCloud 주소: https://www.erdcloud.com/d/5v5C9THyddnhXEpKg
API 명세서 Postman 주소: https://documenter.getpostman.com/view/21511170/2s7YYu7imm
서버 소스코드 Github 주소: https://github.com/eyoo95/perform-server-aws
