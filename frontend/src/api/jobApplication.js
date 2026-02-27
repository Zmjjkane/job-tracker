const BASE_URL = "http://localhost:8080/api/job-applications";

export function fetchJobApplications() {
    return fetch(BASE_URL).then((res) => {
        // 相当于"HTTP " + res.status, 反引号更好读, 可以直接加变量配合${}
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        return res.json();
    });
}