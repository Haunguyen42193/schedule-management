# schedule-management
1.	Giới thiệu đề tài
   
- Trong thời đại số hóa hiện nay, việc quản lý lịch trình cá nhân đã trở nên quan trọng hơn bao giờ hết. Cuộc sống hiện đại đòi hỏi chúng ta phải đối mặt với nhiều hoạt động và nhiệm vụ khác nhau, từ công việc, học tập, gia đình, sở thích cá nhân cho đến các sự kiện xã hội. Vì thế việc xây dựng nên ứng dụng quản lý lịch trình cá nhân là rất cần thiết, nó giúp người dùng tăng cường khả năng quản lý thời gian, đảm bảo hiệu suất trong công việc và đời sống.
- Ứng dụng quản lý lịch trình cá nhân là một ứng dụng giúp người dùng có thể quản lý các công việc của mình bằng cách đặt lịch trước. Với ứng dụng này người dùng có thể thêm các lịch trình, quản lý nó, hoặc thêm các danh mục cho mình để thuận tiện hơn trong việc sử dụng, ngoài ra ứng dụng còn có các chức năng khác như thông báo cho người dùng khi đến thời gian thực hiện lịch trình, xem các lịch trình đã hoàn thành.

2.	Các bước thực hiện đề tài
   
2.1. Thiết kế giao diện ứng dụng:

- Xác định các giao diện cần thiết trong ứng dụng, bao gồm trang đăng nhập, trang đăng ký, trang quản lý người dùng, trang quản lý danh mục, trang xem lịch trình, v.v.
- Vẽ layout và thiết kế giao diện cho từng trang, bao gồm việc xác định vị trí của các thành phần như nút, biểu mẫu, danh sách, v.v.

2.2 Thiết kế cơ sở dữ liệu:

- Xác định các thực thể cần lưu trữ trong cơ sở dữ liệu như Người dùng, Người quản trị, Role, Task, Thông báo, Category, Media.

2.3 Thiết kế các bảng cho mỗi thực thể và xác định các cột cần thiết.

- Thiết lập mối quan hệ giữa các bảng, ví dụ: mối quan hệ 1-n (một-người dùng có thể có nhiều Task), mối quan hệ n-n (nhiều Task thuộc nhiều Category), v.v.

2.4 Tạo các chức năng cho ứng dụng:

- Lập trình các chức năng theo yêu cầu như đăng nhập, đăng ký, thêm/sửa/xóa người dùng, thêm/sửa/xóa danh mục, tạo/sửa/xóa Task, thêm/xóa thông báo, v.v.
- Xử lý các chức năng liên quan đến việc gán Role cho người dùng, quản lý lịch trình, thống kê, kiểm soát trùng lặp, thông báo âm thanh.

2.5 Đảm bảo tính bảo mật cho ứng dụng:

- Sử dụng các biện pháp bảo mật như mã hóa mật khẩu, xác thực người dùng, kiểm tra và xử lý dữ liệu đầu vào để đề phòng các cuộc tấn công như SQL injection, cross-site scripting (XSS), v.v.
- Xác định quyền truy cập cho từng vai trò và người dùng để đảm bảo rằng thông tin và chức năng chỉ được truy cập bởi người dùng có quyền thích hợp.

2.6 Kiểm tra lỗi và đảm bảo tính ổn định:

- Thực hiện kiểm thử thử nghiệm trên ứng dụng để xác định và sửa các lỗi còn tồn tại.
- Đảm bảo tính ổn định và hiệu suất của ứng dụng dưới nhiều tình huống sử dụng khác nhau.

3.	Mô tả dữ liệu
3.1 Lược đồ cơ sở dữ liệu
Role (RoleID, RoleName)
Users (UserID, Username, Password, Activity, PhoneNumber, Address, Email, #RoleID)
Categories (CategoryID, CategoryName, Description)
Task(TaskID, #UserID, taskTitle, TaskDescription, completed, startTime, #categoryID)
Audio(AudioId, name, filePath, #taskId)
3.2 Use Case
  	
![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/57c3e1a8-ff71-44eb-a85e-38bb2d357641)

3.3 Mối quan hệ giữa các bảng
 
![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/2f8df57b-fa84-497d-a41b-9fffb6e8bf9c)

4.	Các chức năng của ứng dụng
4.1 Chức năng của Admin
  	
- Quản lý User:
+ Thêm, xóa, sửa thông tin người dùng: Admin có quyền thêm mới người dùng vào hệ thống, xóa người dùng không còn cần thiết và sửa đổi thông tin cá nhân của họ nếu cần.
+ Gán vai trò (Role) cho người dùng: Admin có khả năng gán các vai trò khác nhau cho người dùng, ví dụ như vai trò người dùng thường, vai trò quản trị viên.
- Quản lý danh mục: Thêm, xóa, sửa danh mục: Admin có thể thêm mới, xóa hoặc chỉnh sửa các danh mục trong ứng dụng. Điều này giúp người dùng có thể phân loại và quản lý các nhiệm vụ (Tasks) dễ dàng hơn.
- Quản lý âm thanh nhắc nhở: Thêm, xóa, sửa âm thanh thông báo: Admin có khả năng quản lý các âm thanh thông báo mà người dùng có thể sử dụng để nhắc nhở về các sự kiện hoặc nhiệm vụ trong ứng dụng.
- Quản lý lịch trình: Thống kê lịch trình của người dùng: Admin có thể thống kê và xem tổng quan về lịch trình của từng người dùng, bao gồm các sự kiện đã được thêm và lên kế hoạch.
- Vai trò của Admin trong ứng dụng là quản lý và điều chỉnh các khía cạnh quan trọng như thông tin người dùng, danh mục, âm thanh nhắc nhở và lịch trình để đảm bảo hoạt động của ứng dụng diễn ra một cách hiệu quả và có tổ chức.
  
4.2. Các chức năng của User

- Đăng ký thành viên: Người dùng có thể tạo tài khoản mới để trở thành thành viên của ứng dụng. Thông qua quá trình này, họ cung cấp thông tin cá nhân cần thiết để tạo tài khoản.
- Đăng nhập: Sau khi đã đăng ký, người dùng có thể sử dụng tên đăng nhập và mật khẩu để đăng nhập vào tài khoản của mình.
- Tạo danh mục: Người dùng có thể tạo các danh mục riêng để phân loại và tổ chức các lịch trình cá nhân một cách dễ dàng.
- Kiểm soát lịch trình trùng nhau:Người dùng có thể kiểm tra xem các lịch trình mà họ đã tạo có bị trùng lặp không, giúp họ tránh xung đột và đảm bảo sắp xếp thời gian hiệu quả.
- Tính năng thông báo lịch trình bằng âm thanh:Ứng dụng cung cấp tính năng thông báo lịch trình bằng âm thanh để nhắc nhở người dùng về các sự kiện hoặc nhiệm vụ sắp diễn ra.
- Xem lịch trình: Người dùng có thể xem các lịch trình mà họ đã tạo trước đó, bao gồm cả thông tin chi tiết về thời gian, nội dung và danh mục liên quan.
- Thống kê lịch trình theo ngày, giờ: Người dùng có khả năng thống kê và phân loại các lịch trình theo ngày và giờ, giúp họ dễ dàng theo dõi và quản lý các sự kiện trong tương lai.
- Chức năng của người dùng trong ứng dụng tập trung vào việc tạo, quản lý và theo dõi lịch trình cá nhân một cách hiệu quả, bao gồm cả việc tạo danh mục, kiểm soát trùng lặp, sử dụng thông báo âm thanh và thống kê lịch trình theo ngày, giờ để đảm bảo sắp xếp thời gian một cách hợp lý.

5.	Giao diện
* Giao diện khi start app
![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/60ec19c2-208d-4e8a-9284-03a84357206f)

* Giao diện đăng ký
 
![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/a9ebc480-8268-4085-a249-7b51ff247b76)

* Giao diện đăng nhâp

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/89b1b4f8-c522-48e3-be25-f6824dccd60f)
 
* Giao diện Admin

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/6ef6a7a2-23cc-4894-90fe-53e7abbefb2a)

* Giao diện quản lý user

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/2b90ca42-64de-4013-b2e8-94f0426dd074)

* Giao diện add user

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/1d0dde9f-4826-4c8a-abbf-2ae866cc32a4)

* Giao diện Infomation User

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/b0038d82-6b89-4e31-be16-33c5c3cb17b2)

* Giao diện Quản lý Task

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/9f33165a-094d-4866-8f33-f9a2d5e1c871)

* Giao diện Add Task

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/37c40a5f-14e3-4738-b16d-7a134b5dbab8)

* Giao diện Task Detail

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/f792f239-9fa2-4bc3-85ec-168403001437)

* Giao diện quản lý Category

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/4c8d52c8-9829-4f78-ae5b-776f1e04c60e)

* Giao diện Category Information

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/a2f4b01e-442d-4df0-839a-66999e9f0048)

* Giao diện Add Category

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/35214ff0-26dd-4de8-84b4-48649c35e35e)

* Giao diện quản lý Audio

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/21ec8a44-0c90-43b6-8b36-cec7514127ed)

* Giao diện Audio Infomation

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/2ad5e9e3-fc0f-4f5e-86e1-c413c362d99c)
 
* Giao diện Add Audio

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/6b1cdb10-4d4e-4f41-bef4-1f3804543bb9)

* Giao diện chính dành cho User

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/23679dc9-649e-4519-85c8-6a0c7ba219ec)
 
* Giao diện Add Task

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/383f4788-1cb6-4392-a55f-e341475160cf)

* Giao diện History

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/54e30216-1a04-47cf-809e-1fe495e9e574)

* Giao diện Detail Task

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/edab1d91-3d5f-4737-89af-51ea8087b84b)

* Giao diện List Category

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/0e212a6a-d6e2-4fed-9699-cc89ebc677d9)

* Giao diện Detail Category

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/dfb2285c-7f23-4d08-bcf3-36da8871db00)

* Giao diện Add Category

![image](https://github.com/Haunguyen42193/schedule-management/assets/92702518/05ad8c8a-853b-4937-8a04-3d35d89c96c5)
 
KẾT LUẬN
1.	Kết quả đạt được
- Ứng dụng app ToDoList có giao diện thân thiện, dễ sử dụng, có đầy đủ các chức năng theo yêu cầu của đề tài.
- Sau khi hoàn thành nhóm em đã biết thêm sâu hơn về lập trình di động, tăng cường khả năng làm việc nhóm, có thêm kinh nghiệm về lập trình.
- Hỗ trợ nhiều chức năng như đăng ký thành viên, đăng nhập, tạo(sửa, xóa) danh mục, tạo(sửa, xóa) task, xem lịch sử task, kiểm soát task trùng nhau, thống kê lịch trình theo loại ngày, giờ,.…
- Về phần admin thì có quản lý User: thêm, sửa, xóa, chuyển vai trò. Quản lý danh mục: thêm, sửa, xóa, danh mục. Quản lý âm thanh nhắc nhở: thêm, xóa, sửa. Quản lý lịch trình: thống kê theo từng user. Chọn âm thanh từ bộ nhớ điện thoại. 
2.	Hạn chế của đề tài.
- Đề tài này không cần thiết phải quản lý danh mục, quản lý các task hay quản lý các audio,…
- Một số chức năng còn chưa hoạt động ổn định.
3.	Phát triển đề tài trong tương lai.
- Custom lại giao diện đẹp, dễ sử dụng nhất.
- Khi người dùng chọn thêm task thì có thêm category hoặc hiển thị ra danh sách các category mà con người hay sử dụng nhất (ví dụ như công việc, mua sắm, học tập,...). tích hợp lắng nghe giọng nói từ người dùng,…
- Có setting để chọn màu sắc giao diện, chữ,..
- Có tích hợp chuyển đổi ngôn ngữ, phục vụ cho tất cả mọi người trên thế giới đều có thể sử dụng ứng dụng.
- Tích hợp vị trí google map vào calendar.
- Cuối cùng phát triển thành một ứng dụng như todolist hoàn chỉnh và upload chplay cho mọi người sử dụng để phục vụ nhu cầu quản lý công việc cá nhân.
