import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* config options here */
  transpilePackages: ['antd', '@ant-design/icons'],
  // 解决跨域问题
  async rewrites() {
    return [
      {
        source: '/phantom_im/api/v1/:path*',
        destination: 'http://localhost:8789/phantom_im/api/v1/:path*' // 替换为你的后端API地址
      }
    ]
  }
};

export default nextConfig;
