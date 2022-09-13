package com.luvris2.publicperfomancedisplayapp.resource;

import android.util.Log;

public class SidoSubClassify {
    // 지역 확인 후 지역 코드 변환
    public static String sidoSubClassify(String sidoSubLocation) {
        Log.i("MyTest sidoclassify", "" + sidoSubLocation);
        if (sidoSubLocation.contains("서울특별시 종로구")) {
            return "1111";
        } else if (sidoSubLocation.contains("서울특별시 중구")) {
            return "1114";
        } else if (sidoSubLocation.contains("서울특별시 용산구")) {
            return "1117";
        } else if (sidoSubLocation.contains("서울특별시 성동구")) {
            return "1120";
        } else if (sidoSubLocation.contains("서울특별시 광진구")) {
            return "1121";
        } else if (sidoSubLocation.contains("서울특별시 동대문구")) {
            return "1123";
        } else if (sidoSubLocation.contains("서울특별시 중랑구")) {
            return "1126";
        } else if (sidoSubLocation.contains("서울특별시 성북구")) {
            return "1129";
        } else if (sidoSubLocation.contains("서울특별시 강북구")) {
            return "1130";
        } else if (sidoSubLocation.contains("서울특별시 도봉구")) {
            return "1132";
        } else if (sidoSubLocation.contains("서울특별시 노원구")) {
            return "1135";
        } else if (sidoSubLocation.contains("서울특별시 은평구")) {
            return "1138";
        } else if (sidoSubLocation.contains("서울특별시 서대문구")) {
            return "1141";
        } else if (sidoSubLocation.contains("서울특별시 마포구")) {
            return "1144";
        } else if (sidoSubLocation.contains("서울특별시 양천구")) {
            return "1147";
        } else if (sidoSubLocation.contains("서울특별시 강서구")) {
            return "1150";
        } else if (sidoSubLocation.contains("서울특별시 구로구")) {
            return "1153";
        } else if (sidoSubLocation.contains("서울특별시 금천구")) {
            return "1154";
        } else if (sidoSubLocation.contains("서울특별시 영등포구")) {
            return "1156";
        } else if (sidoSubLocation.contains("서울특별시 동작구")) {
            return "1159";
        } else if (sidoSubLocation.contains("서울특별시 관악구")) {
            return "1162";
        } else if (sidoSubLocation.contains("서울특별시 서초구")) {
            return "1165";
        } else if (sidoSubLocation.contains("서울특별시 강남구")) {
            return "1168";
        } else if (sidoSubLocation.contains("서울특별시 송파구")) {
            return "1171";
        } else if (sidoSubLocation.contains("서울특별시 강동구")) {
            return "1174";
        } else if (sidoSubLocation.contains("부산광역시")) {
            return "2600";
        } else if (sidoSubLocation.contains("부산광역시 중구")) {
            return "2611";
        } else if (sidoSubLocation.contains("부산광역시 서구")) {
            return "2614";
        } else if (sidoSubLocation.contains("부산광역시 동구")) {
            return "2617";
        } else if (sidoSubLocation.contains("부산광역시 영도구")) {
            return "2620";
        } else if (sidoSubLocation.contains("부산광역시 부산진구")) {
            return "2623";
        } else if (sidoSubLocation.contains("부산광역시 동래구")) {
            return "2626";
        } else if (sidoSubLocation.contains("부산광역시 남구")) {
            return "2629";
        } else if (sidoSubLocation.contains("부산광역시 북구")) {
            return "2632";
        } else if (sidoSubLocation.contains("부산광역시 해운대구")) {
            return "2635";
        } else if (sidoSubLocation.contains("부산광역시 사하구")) {
            return "2638";
        } else if (sidoSubLocation.contains("부산광역시 금정구")) {
            return "2641";
        } else if (sidoSubLocation.contains("부산광역시 강서구")) {
            return "2644";
        } else if (sidoSubLocation.contains("부산광역시 연제구")) {
            return "2647";
        } else if (sidoSubLocation.contains("부산광역시 수영구")) {
            return "2650";
        } else if (sidoSubLocation.contains("부산광역시 사상구")) {
            return "2653";
        } else if (sidoSubLocation.contains("부산광역시 기장군")) {
            return "2671";
        } else if (sidoSubLocation.contains("대구광역시")) {
            return "2700";
        } else if (sidoSubLocation.contains("대구광역시 중구")) {
            return "2711";
        } else if (sidoSubLocation.contains("대구광역시 동구")) {
            return "2714";
        } else if (sidoSubLocation.contains("대구광역시 서구")) {
            return "2717";
        } else if (sidoSubLocation.contains("대구광역시 남구")) {
            return "2720";
        } else if (sidoSubLocation.contains("대구광역시 북구")) {
            return "2723";
        } else if (sidoSubLocation.contains("대구광역시 수성구")) {
            return "2726";
        } else if (sidoSubLocation.contains("대구광역시 달서구")) {
            return "2729";
        } else if (sidoSubLocation.contains("인천광역시")) {
            return "2800";
        } else if (sidoSubLocation.contains("인천광역시 중구")) {
            return "2811";
        } else if (sidoSubLocation.contains("인천광역시 동구")) {
            return "2814";
        } else if (sidoSubLocation.contains("인천광역시 남구")) {
            return "2817";
        } else if (sidoSubLocation.contains("인천광역시 연수구")) {
            return "2818";
        } else if (sidoSubLocation.contains("인천광역시 남동구")) {
            return "2820";
        } else if (sidoSubLocation.contains("인천광역시 부평구")) {
            return "2823";
        } else if (sidoSubLocation.contains("인천광역시 계양구")) {
            return "2824";
        } else if (sidoSubLocation.contains("인천광역시 서구")) {
            return "2826";
        } else if (sidoSubLocation.contains("인천광역시 강화군")) {
            return "2871";
        } else if (sidoSubLocation.contains("인천광역시 옹진군")) {
            return "2872";
        } else if (sidoSubLocation.contains("광주광역시")) {
            return "2900";
        } else if (sidoSubLocation.contains("광주광역시 동구")) {
            return "2911";
        } else if (sidoSubLocation.contains("광주광역시 서구")) {
            return "2914";
        } else if (sidoSubLocation.contains("광주광역시 남구")) {
            return "2915";
        } else if (sidoSubLocation.contains("광주광역시 북구")) {
            return "2917";
        } else if (sidoSubLocation.contains("광주광역시 광산구")) {
            return "2920";
        } else if (sidoSubLocation.contains("대전광역시")) {
            return "3000";
        } else if (sidoSubLocation.contains("대전광역시 동구")) {
            return "3011";
        } else if (sidoSubLocation.contains("대전광역시 중구")) {
            return "3014";
        } else if (sidoSubLocation.contains("대전광역시 서구")) {
            return "3017";
        } else if (sidoSubLocation.contains("대전광역시 유성구")) {
            return "3020";
        } else if (sidoSubLocation.contains("대전광역시 대덕구")) {
            return "3023";
        } else if (sidoSubLocation.contains("울산광역시")) {
            return "3100";
        } else if (sidoSubLocation.contains("울산광역시 중구")) {
            return "3111";
        } else if (sidoSubLocation.contains("울산광역시 남구")) {
            return "3114";
        } else if (sidoSubLocation.contains("울산광역시 동구")) {
            return "3117";
        } else if (sidoSubLocation.contains("울산광역시 북구")) {
            return "3120";
        } else if (sidoSubLocation.contains("울산광역시 울주군")) {
            return "3171";
        } else if (sidoSubLocation.contains("세종특별자치시")) {
            return "3600";
        } else if (sidoSubLocation.contains("경기도")) {
            return "4100";
        } else if (sidoSubLocation.contains("경기도 수원시")) {
            return "4111";
        } else if (sidoSubLocation.contains("경기도 성남시")) {
            return "4113";
        } else if (sidoSubLocation.contains("경기도 의정부시")) {
            return "4115";
        } else if (sidoSubLocation.contains("경기도 안양시")) {
            return "4117";
        } else if (sidoSubLocation.contains("경기도 부천시")) {
            return "4119";
        } else if (sidoSubLocation.contains("경기도 광명시")) {
            return "4121";
        } else if (sidoSubLocation.contains("경기도 평택시")) {
            return "4122";
        } else if (sidoSubLocation.contains("경기도 동두천시")) {
            return "4125";
        } else if (sidoSubLocation.contains("경기도 안산시")) {
            return "4127";
        } else if (sidoSubLocation.contains("경기도 고양시")) {
            return "4128";
        } else if (sidoSubLocation.contains("경기도 과천시")) {
            return "4129";
        } else if (sidoSubLocation.contains("경기도 구리시")) {
            return "4131";
        } else if (sidoSubLocation.contains("경기도 남양주시")) {
            return "4136";
        } else if (sidoSubLocation.contains("경기도 오산시")) {
            return "4137";
        } else if (sidoSubLocation.contains("경기도 시흥시")) {
            return "4139";
        } else if (sidoSubLocation.contains("경기도 군포시")) {
            return "4141";
        } else if (sidoSubLocation.contains("경기도 의왕시")) {
            return "4143";
        } else if (sidoSubLocation.contains("경기도 하남시")) {
            return "4145";
        } else if (sidoSubLocation.contains("경기도 용인시")) {
            return "4146";
        } else if (sidoSubLocation.contains("경기도 파주시")) {
            return "4148";
        } else if (sidoSubLocation.contains("경기도 이천시")) {
            return "4150";
        } else if (sidoSubLocation.contains("경기도 안성시")) {
            return "4155";
        } else if (sidoSubLocation.contains("경기도 김포시")) {
            return "4157";
        } else if (sidoSubLocation.contains("경기도 화성시")) {
            return "4159";
        } else if (sidoSubLocation.contains("경기도 광주시")) {
            return "4161";
        } else if (sidoSubLocation.contains("경기도 양주시")) {
            return "4163";
        } else if (sidoSubLocation.contains("경기도 포천시")) {
            return "4165";
        } else if (sidoSubLocation.contains("경기도 여주군")) {
            return "4173";
        } else if (sidoSubLocation.contains("경기도 연천군")) {
            return "4180";
        } else if (sidoSubLocation.contains("경기도 가평군")) {
            return "4182";
        } else if (sidoSubLocation.contains("경기도 양평군")) {
            return "4183";
        } else if (sidoSubLocation.contains("강원도")) {
            return "4200";
        } else if (sidoSubLocation.contains("강원도 춘천시")) {
            return "4211";
        } else if (sidoSubLocation.contains("강원도 원주시")) {
            return "4213";
        } else if (sidoSubLocation.contains("강원도 강릉시")) {
            return "4215";
        } else if (sidoSubLocation.contains("강원도 동해시")) {
            return "4217";
        } else if (sidoSubLocation.contains("강원도 태백시")) {
            return "4219";
        } else if (sidoSubLocation.contains("강원도 속초시")) {
            return "4221";
        } else if (sidoSubLocation.contains("강원도 삼척시")) {
            return "4223";
        } else if (sidoSubLocation.contains("강원도 홍천군")) {
            return "4272";
        } else if (sidoSubLocation.contains("강원도 횡성군")) {
            return "4273";
        } else if (sidoSubLocation.contains("강원도 영월군")) {
            return "4275";
        } else if (sidoSubLocation.contains("강원도 평창군")) {
            return "4276";
        } else if (sidoSubLocation.contains("강원도 정선군")) {
            return "4277";
        } else if (sidoSubLocation.contains("강원도 철원군")) {
            return "4278";
        } else if (sidoSubLocation.contains("강원도 화천군")) {
            return "4279";
        } else if (sidoSubLocation.contains("강원도 양구군")) {
            return "4280";
        } else if (sidoSubLocation.contains("강원도 인제군")) {
            return "4281";
        } else if (sidoSubLocation.contains("강원도 고성군")) {
            return "4282";
        } else if (sidoSubLocation.contains("강원도 양양군")) {
            return "4283";
        } else if (sidoSubLocation.contains("충청북도")) {
            return "4300";
        } else if (sidoSubLocation.contains("충청북도 청주시")) {
            return "4311";
        } else if (sidoSubLocation.contains("충청북도 충주시")) {
            return "4313";
        } else if (sidoSubLocation.contains("충청북도 제천시")) {
            return "4315";
        } else if (sidoSubLocation.contains("충청북도 청원군")) {
            return "4371";
        } else if (sidoSubLocation.contains("충청북도 보은군")) {
            return "4372";
        } else if (sidoSubLocation.contains("충청북도 옥천군")) {
            return "4373";
        } else if (sidoSubLocation.contains("충청북도 영동군")) {
            return "4374";
        } else if (sidoSubLocation.contains("충청북도 진천군")) {
            return "4375";
        } else if (sidoSubLocation.contains("충청북도 괴산군")) {
            return "4376";
        } else if (sidoSubLocation.contains("충청북도 음성군")) {
            return "4377";
        } else if (sidoSubLocation.contains("충청북도 단양군")) {
            return "4380";
        } else if (sidoSubLocation.contains("충청남도")) {
            return "4400";
        } else if (sidoSubLocation.contains("충청남도 천안시")) {
            return "4413";
        } else if (sidoSubLocation.contains("충청남도 공주시")) {
            return "4415";
        } else if (sidoSubLocation.contains("충청남도 보령시")) {
            return "4418";
        } else if (sidoSubLocation.contains("충청남도 아산시")) {
            return "4420";
        } else if (sidoSubLocation.contains("충청남도 서산시")) {
            return "4421";
        } else if (sidoSubLocation.contains("충청남도 논산시")) {
            return "4423";
        } else if (sidoSubLocation.contains("충청남도 계룡시")) {
            return "4425";
        } else if (sidoSubLocation.contains("충청남도 당진시")) {
            return "4427";
        } else if (sidoSubLocation.contains("충청남도 금산군")) {
            return "4471";
        } else if (sidoSubLocation.contains("충청남도 부여군")) {
            return "4476";
        } else if (sidoSubLocation.contains("충청남도 서천군")) {
            return "4477";
        } else if (sidoSubLocation.contains("충청남도 청양군")) {
            return "4479";
        } else if (sidoSubLocation.contains("충청남도 홍성군")) {
            return "4480";
        } else if (sidoSubLocation.contains("충청남도 예산군")) {
            return "4481";
        } else if (sidoSubLocation.contains("충청남도 태안군")) {
            return "4482";
        } else if (sidoSubLocation.contains("전라북도")) {
            return "4500";
        } else if (sidoSubLocation.contains("전라북도 전주시")) {
            return "4511";
        } else if (sidoSubLocation.contains("전라북도 군산시")) {
            return "4513";
        } else if (sidoSubLocation.contains("전라북도 익산시")) {
            return "4514";
        } else if (sidoSubLocation.contains("전라북도 정읍시")) {
            return "4518";
        } else if (sidoSubLocation.contains("전라북도 남원시")) {
            return "4519";
        } else if (sidoSubLocation.contains("전라북도 김제시")) {
            return "4521";
        } else if (sidoSubLocation.contains("전라북도 완주군")) {
            return "4571";
        } else if (sidoSubLocation.contains("전라북도 진안군")) {
            return "4572";
        } else if (sidoSubLocation.contains("전라북도 무주군")) {
            return "4573";
        } else if (sidoSubLocation.contains("전라북도 장수군")) {
            return "4574";
        } else if (sidoSubLocation.contains("전라북도 임실군")) {
            return "4575";
        } else if (sidoSubLocation.contains("전라북도 순창군")) {
            return "4577";
        } else if (sidoSubLocation.contains("전라북도 고창군")) {
            return "4579";
        } else if (sidoSubLocation.contains("전라북도 부안군")) {
            return "4580";
        } else if (sidoSubLocation.contains("전라남도")) {
            return "4600";
        } else if (sidoSubLocation.contains("전라남도 목포시")) {
            return "4611";
        } else if (sidoSubLocation.contains("전라남도 여수시")) {
            return "4613";
        } else if (sidoSubLocation.contains("전라남도 순천시")) {
            return "4615";
        } else if (sidoSubLocation.contains("전라남도 나주시")) {
            return "4617";
        } else if (sidoSubLocation.contains("전라남도 광양시")) {
            return "4623";
        } else if (sidoSubLocation.contains("전라남도 담양군")) {
            return "4671";
        } else if (sidoSubLocation.contains("전라남도 곡성군")) {
            return "4672";
        } else if (sidoSubLocation.contains("전라남도 구례군")) {
            return "4673";
        } else if (sidoSubLocation.contains("전라남도 고흥군")) {
            return "4677";
        } else if (sidoSubLocation.contains("전라남도 보성군")) {
            return "4678";
        } else if (sidoSubLocation.contains("전라남도 화순군")) {
            return "4679";
        } else if (sidoSubLocation.contains("전라남도 장흥군")) {
            return "4680";
        } else if (sidoSubLocation.contains("전라남도 강진군")) {
            return "4681";
        } else if (sidoSubLocation.contains("전라남도 해남군")) {
            return "4682";
        } else if (sidoSubLocation.contains("전라남도 영암군")) {
            return "4683";
        } else if (sidoSubLocation.contains("전라남도 무안군")) {
            return "4684";
        } else if (sidoSubLocation.contains("전라남도 함평군")) {
            return "4686";
        } else if (sidoSubLocation.contains("전라남도 영광군")) {
            return "4687";
        } else if (sidoSubLocation.contains("전라남도 장성군")) {
            return "4688";
        } else if (sidoSubLocation.contains("전라남도 완도군")) {
            return "4689";
        } else if (sidoSubLocation.contains("전라남도 진도군")) {
            return "4690";
        } else if (sidoSubLocation.contains("전라남도 신안군")) {
            return "4691";
        } else if (sidoSubLocation.contains("경상북도")) {
            return "4700";
        } else if (sidoSubLocation.contains("경상북도 포항시")) {
            return "4711";
        } else if (sidoSubLocation.contains("경상북도 경주시")) {
            return "4713";
        } else if (sidoSubLocation.contains("경상북도 김천시")) {
            return "4715";
        } else if (sidoSubLocation.contains("경상북도 안동시")) {
            return "4717";
        } else if (sidoSubLocation.contains("경상북도 구미시")) {
            return "4719";
        } else if (sidoSubLocation.contains("경상북도 영주시")) {
            return "4721";
        } else if (sidoSubLocation.contains("경상북도 영천시")) {
            return "4723";
        } else if (sidoSubLocation.contains("경상북도 상주시")) {
            return "4725";
        } else if (sidoSubLocation.contains("경상북도 문경시")) {
            return "4728";
        } else if (sidoSubLocation.contains("경상북도 경산시")) {
            return "4729";
        } else if (sidoSubLocation.contains("경상북도 군위군")) {
            return "4772";
        } else if (sidoSubLocation.contains("경상북도 의성군")) {
            return "4773";
        } else if (sidoSubLocation.contains("경상북도 청송군")) {
            return "4775";
        } else if (sidoSubLocation.contains("경상북도 영양군")) {
            return "4776";
        } else if (sidoSubLocation.contains("경상북도 영덕군")) {
            return "4777";
        } else if (sidoSubLocation.contains("경상북도 청도군")) {
            return "4782";
        } else if (sidoSubLocation.contains("경상북도 고령군")) {
            return "4783";
        } else if (sidoSubLocation.contains("경상북도 성주군")) {
            return "4784";
        } else if (sidoSubLocation.contains("경상북도 칠곡군")) {
            return "4785";
        } else if (sidoSubLocation.contains("경상북도 예천군")) {
            return "4790";
        } else if (sidoSubLocation.contains("경상북도 봉화군")) {
            return "4792";
        } else if (sidoSubLocation.contains("경상북도 울진군")) {
            return "4793";
        } else if (sidoSubLocation.contains("경상북도 울릉군")) {
            return "4794";
        } else if (sidoSubLocation.contains("경상남도")) {
            return "4800";
        } else if (sidoSubLocation.contains("경상남도 창원시")) {
            return "4812";
        } else if (sidoSubLocation.contains("경상남도 진주시")) {
            return "4817";
        } else if (sidoSubLocation.contains("경상남도 통영시")) {
            return "4822";
        } else if (sidoSubLocation.contains("경상남도 사천시")) {
            return "4824";
        } else if (sidoSubLocation.contains("경상남도 김해시")) {
            return "4825";
        } else if (sidoSubLocation.contains("경상남도 밀양시")) {
            return "4827";
        } else if (sidoSubLocation.contains("경상남도 거제시")) {
            return "4831";
        } else if (sidoSubLocation.contains("경상남도 양산시")) {
            return "4833";
        } else if (sidoSubLocation.contains("경상남도 의령군")) {
            return "4872";
        } else if (sidoSubLocation.contains("경상남도 함안군")) {
            return "4873";
        } else if (sidoSubLocation.contains("경상남도 창녕군")) {
            return "4874";
        } else if (sidoSubLocation.contains("경상남도 고성군")) {
            return "4882";
        } else if (sidoSubLocation.contains("경상남도 남해군")) {
            return "4884";
        } else if (sidoSubLocation.contains("경상남도 하동군")) {
            return "4885";
        } else if (sidoSubLocation.contains("경상남도 산청군")) {
            return "4886";
        } else if (sidoSubLocation.contains("경상남도 함양군")) {
            return "4887";
        } else if (sidoSubLocation.contains("경상남도 거창군")) {
            return "4888";
        } else if (sidoSubLocation.contains("경상남도 합천군")) {
            return "4889";
        } else if (sidoSubLocation.contains("제주특별자치도")) {
            return "5000";
        } else if (sidoSubLocation.contains("제주특별자치도 제주시")) {
            return "5011";
        } else if (sidoSubLocation.contains("제주특별자치도 서귀포시")) {
            return "5013";
        }

        return "error";
    }
}