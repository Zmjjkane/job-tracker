import { useEffect, useState } from "react";
import { fetchJobApplications } from "../api/jobApplication";

export default function JobListPage() {
  // jobs: 用来存后端返回的列表数据
  // 初始用 null 表示“还没开始/还没拿到数据”，方便显示 Loading
  const [jobs, setJobs] = useState(null);

  // error: 用字符串存错误信息
  // 初始 "" 表示“没有错误”（falsy）
  // 一旦有错误就 set 成非空字符串（truthy），页面就能显示错误
  const [error, setError] = useState("");

  useEffect(() => {
    // useEffect + []：表示组件第一次渲染后执行一次（相当于“页面加载后请求数据”）
    fetchJobApplications()
      .then((data) => {
        // data 就是解析出来的 JS 数据
        // 比如后端返回 []，这里 data 就是 []
        // 后端返回 [{...}, {...}]，这里就是数组

        // 小兜底：确保 jobs 最终是数组，避免后续 jobs.length / jobs.map 报错
        // 你现在后端返回就是数组，所以这句不会改变行为，只是更稳
        setJobs(Array.isArray(data) ? data : []);
      })
      .catch((e) => {
        // 把错误转成字符串存起来，方便直接渲染到页面
        setError(String(e));
      });
  }, []);

  // 如果 error 是非空字符串（truthy），直接显示错误页, jsx里""为false
  if (error) return <div>Error: {error}</div>;

  // 如果 jobs 还是 null，说明请求还没结束，显示 Loading
  if (jobs === null) return <div>Loading...</div>;

  // 走到这里说明 jobs 已经是数组/对象了，可以渲染数据
  // 注意：我们上面做了 Array.isArray 兜底，所以这里 jobs 一定是数组
  return (
    <div style={{ padding: 24 }}>
      <h2>Job List</h2>

      {jobs.length === 0 ? (
        <div>No job applications yet.</div>
      ) : (
        <table border="1" cellPadding="8" style={{ borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th>ID</th>
              <th>Company</th>
              <th>Position</th>
              <th>Status</th>
              <th>Applied Date</th>
            </tr>
          </thead>
          <tbody>
            {jobs.map((job) => (
              <tr key={job.id}>
                <td>{job.id}</td>
                <td>{job.company}</td>
                <td>{job.position}</td>
                <td>{job.status}</td>
                <td>{job.appliedDate}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}