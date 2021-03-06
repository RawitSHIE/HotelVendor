# HotelVendor
เป็นการให้บริการสำหรับผู้ที่ต้องการหาโรงแรมเพื่อเข้าพัก
โดยการให้บริการทางด้าน

- การค้นหาโรงแรม
- การจองห้องของโรงแรม

โดยระบบนี้จะมุ่งเน้นการหาห้องพักให้สำหรับลูกค้า โดยลูกค้าสามารถ Filter หาห้องพักจาก สถานที่ วันเวลา หรือ Rate ราคาได้
และลูกค้าสามารถติดตามการจองของตนได้

โดยจะมีการรวมการจัดการห้องของทางโรงแรมโดยโรงแรมสามารถ
- สามารถเพิ่มหรือลดห้องได้

โดยระบบจะทำการจัดการ ตารางการจองและห้องให้สำหรับโรงแรม
และ สามารถระบุวันว่างของห้อง จากวันที่ลูกค้าเลือกได้ และโรงแรมสามารถติดตามการจองห้องของโรงแรมเองได้

## API Structure overview Plan
> Not yet finalize
<p align="center">
  <image src="ReadmeResource/apiStructure2.png" alt="api overview"/>
</p>

- Interface คือหน้าการใช้งานของระบบ
- Possible Basic Require API คือ API ที่มีหน้าที่ใน Interface นั้นๆ
- Possiblne Outsource API คือ API ของ Service อื่นที่เป็นเป็นไปได้เป็นไปได้


## API Endpoint ที่จำเป็นต่อ Busniess

### Feature Endpoint
- API ในการรับผิดชอบการ Filter โรงแรมตาม Hotel name การ Search ของลูกค้า
- API ในการรับชอบการตรวจสอบ สถานะของห้องในการจองในช่วงเวลานั้นโดยไม่เกิดการทับกันของการจอง
- API ในการ Update Entity Room โดยยึดจาก Entity Booking
- API ในการ Add และ Update Entity Room ตามโรงแรมที่ต้องการ
- API ในการ Add และ Update Entity Hotel
- API Authentication

### Entity
- Room
- Hotel
- User
- Booking


## Json ของ Entity มีดังนี้
### Hotel
```json
{
	"hotelName" : "string",
	"country" : "string",
	"provinceState" : "string",
	"district" : "string",
	"street" : "string",
	"additionalDetail" : "string",
	"availible" : "boolean",
	"tel" : ["string"],
	"email" : ["string"]
}
```

### Room
```json
 { 
  "roomTypeName" : "string",
  "price" : "number",
  "quantity" : "number",
  "roomTypeImages" : ["string"]
 }
```

### User
```json
{
	"username" : "string",
	"password" : "string",
	"firstName" : "string",
    	"middleName" :"string",
	"lastName" : "string",
	"tel" : ["string"],
	"email" : "string"
}
```

### Booking
```json
{
	"userId" : "number",
	"hotelId" : "number",
	"bookingStatus" : "string",
	"bookingStartDate" : "date-time",
	"bookingEndDate" : "date-time",
	"roomTypeRequests" : [
        {
            "roomTypeName" : "string",
            "price" : "number",
            "quantity" : "number"
        },
        {
            "roomTypeName" : "string",
            "price" : "number",
            "quantity" : "number"
        }
	]
}
```
## API Endpoint
[API Documentation](https://documenter.getpostman.com/view/8387942/SWDzeLz8)

### Hotel
- GET /allhotel
- GET /hoteldetail/{hotelId}
- POST /createhotel
- POST /updatehotel/{hotelId}
- POST /createroomtype/{hotelId}
- GET /fullhoteldetail/{hotelId}
- GET /getallroomtype/{hotelId}
- POST /updateroomtype/{hotelId}/{roomTypeId}

### User
- GET /alluser
- GET /user/{id}
- POST /user
- POST /login
- POST /logout
- GET /user/me
- POST /user/update

### Booking
- POST /createBooking
- GET /getbookingbyuser/{userId}
- GET /getbookingdetail/{bookingId}
- POST /updateBookingStatus/{bookingId}
- GET /getbookingbyhotel/{hotelId}
- GET /hoteldetail/allroomtype/{hotelId}
- POST /freeroom/{hotelId}



> price may vary overtime dedicate price store foreach booking is needed*
# 👥Team Member

|<a href=""><img src="https://avatars3.githubusercontent.com/u/32660620?s=400&v=4" width="100px"></a>  |<a href=""><img src="https://avatars0.githubusercontent.com/u/31315990?s=460&v=4" width="100px"></a>  |<a href=""><img src="https://avatars0.githubusercontent.com/u/32817745?s=460&v=4" width="100px"></a>  | <a href=""><img src="https://avatars0.githubusercontent.com/u/32954674?s=460&v=4" width="100px"></a>  |
| :-: | :-: | :-: | :-: |
|ธงเงิน แย้ม|รวิชญ์ โลหะขจรพันธ์|ศตวรรษ ธิติศุภกุล|สุธี พิละมาตย์
|60070030 |      60070081      |      60070093      | 60070105|
|    [@ifackerx](https://github.com/ifackerx)    |     [@RawitSHIE](https://github.com/RawitSHIE)     |     [@satawatnack](https://github.com/satawatnack)     | [@itisowen](https://github.com/itisowen) |
