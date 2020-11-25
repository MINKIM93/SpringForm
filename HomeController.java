package com.spring.springform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@RequestMapping(value = "inputForm.me")
	public String inputForm() {
		
		return "inputform";
	}
	
	@RequestMapping(value = "inputProcess.me")
	public String inputProcess(EmpVO vo) { // 파라미터로 전달 받는 데이터가 vo에 저장됨. 필드랑 이름이 같아야 함
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try { 
			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String sql = "insert into emp_copy values " + "(?, ?, ?, ?, sysdate, ?, ?, ?)";
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "123456"); //연결객체생성
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getEmpno()); 
			pstmt.setString(2, vo.getEname()); 
			pstmt.setString(3, vo.getJob()); 
			pstmt.setInt(4, vo.getMgr()); 
			pstmt.setDouble(5, vo.getSal()); 
			pstmt.setDouble(6, vo.getComm()); 
			pstmt.setInt(7, vo.getDeptno()); 
			pstmt.executeUpdate(); 
			
		}
		catch(Exception e)
		{
		}
		
		return "index"; // 뷰이름이 옴 데이터 타입 String
	}
	
		@RequestMapping(value = "selectProcess.me")
		public String selectProcess(Model model) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null; // 검색한 결과 저장될 선언문
			ArrayList<EmpVO> list = new ArrayList<EmpVO>(); 
			
			try {
				String driver = "oracle.jdbc.driver.OracleDriver";
				String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, "scott", "123456");
				pstmt = con.prepareStatement("select * from emp_copy order by ename asc");
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					EmpVO empvo = new EmpVO(); // emp 객체 생성
					empvo.setEmpno(rs.getInt("empno"));
					empvo.setEname(rs.getString("ename"));
					empvo.setJob(rs.getString("job"));
					empvo.setMgr(rs.getInt("mgr"));
					empvo.setHiredate(rs.getDate("hiredate"));
					empvo.setSal(rs.getDouble("sal"));
					empvo.setComm(rs.getDouble("comm"));
					empvo.setDeptno(rs.getInt("deptno"));
					list.add(empvo); 
					
				}
				
				model.addAttribute("list", list); // model객체 직접 못만듬 -> 의존성주입방법으로 파라미터로
			}
			catch(Exception e)
			{
			}
			
			return "list";
		}
		
			@RequestMapping(value = "selectDept.me")
			public String selectDept(Model model, 
				@RequestParam(value="deptno", required=false, defaultValue="1") int deptno) { // 파라미터 중 deptno 값을 int deptno에 저장한다. 
				//required = true , required=false 			
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				DeptVO deptvo = null;
				
				try {
					String driver = "oracle.jdbc.driver.OracleDriver";
					String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
					
					Class.forName(driver);
					con = DriverManager.getConnection(url, "scott", "123456");
					
					pstmt = con.prepareStatement("select * from dept_copy where deptno=?");
					
					pstmt.setInt(1, deptno);
					rs = pstmt.executeQuery();
					rs.next();
					deptvo = new DeptVO();
					deptvo.setDeptno(rs.getInt("deptno"));
					deptvo.setDname(rs.getString("dname"));
					deptvo.setLoc(rs.getString("loc"));
					
					model.addAttribute("deptvo", deptvo);
				}
				catch(Exception e)
				{
				}
				
				return "deptview"; 
			}
			@RequestMapping(value = "selectEmpDept.me")
			public String selectEmpDept(Model model) {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null; // 검색한 결과 저장될 선언문
				ArrayList<EmpDeptVO> list = new ArrayList<EmpDeptVO>(); 
				
				try {
					String driver = "oracle.jdbc.driver.OracleDriver";
					String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
					
					Class.forName(driver);
					con = DriverManager.getConnection(url, "scott", "123456");
					pstmt = con.prepareStatement("select e.empno, e.ename, e.job, e.deptno, d.dname, d.loc\n" + 
							"from emp_copy e\n" + 
							"inner join dept_copy d\n" + 
							"on e.deptno = d.deptno" 
							);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						EmpDeptVO empdeptvo = new EmpDeptVO(); 
						empdeptvo.setEmpno(rs.getInt("empno"));
						empdeptvo.setEname(rs.getString("ename"));
						empdeptvo.setJob(rs.getString("job"));
						empdeptvo.setDeptno(rs.getInt("deptno"));
						empdeptvo.setDname(rs.getString("dname"));
						empdeptvo.setLoc(rs.getString("loc"));
				
						list.add(empdeptvo); 
						
					}
					
					model.addAttribute("empdept_list", list);
				}
				catch(Exception e)
				{
				}
				
				return "empdept_list";
			}
}
	
