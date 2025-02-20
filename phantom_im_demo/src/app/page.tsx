'use client';

import React, { useState, useEffect } from 'react';
import { Layout, Form, Input, Button, Card, List, message } from 'antd';
import PhantomIM,{ Client, JMPMessage,getMilliseconds } from '@/libs/phantom_im';
import styles from './page.module.css';

const { Content, Sider } = Layout;

export default function PushTest() {
  const [messages, setMessages] = useState<JMPMessage[]>([]);
  const [sendTime,setSendTime]=useState<string>("");
  const [form] = Form.useForm();
  const queryParams = new URLSearchParams(window.location.search);
  let tag = queryParams.get('tag');
  if(!tag||tag.length==0){
    tag='tag1'
  }
  let user = queryParams.get('user');
  if(!user||user.length==0){
    user='lugao2@window'
  }

  // 连接WebSocket
  useEffect(() => {
    // 从请求地址中获取tag查询参数，参数名称就是tag
    PhantomIM.init('', 'test');
    const client=new Client(user,{
      onConnected:()=>{
        console.log('连接成功');  
        client.setTags([tag])
      },
      onMessage: (msg: JMPMessage) => {
        msg.t=getMilliseconds();
      setMessages((prevMessages) => [...prevMessages, msg]);
    }});
    client.connect()
   
  }, []);

  // 发送推送消息
  const handleSubmit = async (values: { message: string }) => {
    try {
      const msg={
        type: 'tag',
        body:values.message,
        to:[tag]
      }
      setSendTime(getMilliseconds());
      const response = await fetch('/phantom_im/api/v1/push', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(msg),
      });

      if (!response.ok) {
        throw new Error('发送失败');
      }

      message.success('消息发送成功');
      form.resetFields();
    } catch (error) {
      message.error('发送失败：' + error);
    }
  };

  return (
    <Layout className={styles.container}>
      <Sider width={400} theme="light" className={styles.sider}>
        <Card title="发送推送消息" bordered={false}>
          <Form form={form} onFinish={handleSubmit} layout="vertical">
            <Form.Item
              name="message"
              label="消息内容"
              rules={[{ required: true, message: '请输入消息内容' }]}
            >
              <Input.TextArea rows={4} />
            </Form.Item>
            <Form.Item>
              发送时间：{sendTime}
            </Form.Item>
            <Form.Item>

              <Button type="primary" htmlType="submit" block>
                发送消息
              </Button>
            </Form.Item>
          </Form>
        </Card>
      </Sider>
      <Content className={styles.content}>
        <Card title="接收到的消息" bordered={false}>
          <List
            dataSource={messages}
            renderItem={(item, index) => (
              <List.Item>
                <Card type="inner" title={`消息 ${index + 1}  ${item.t}`} style={{ width: '100%' }}>
                  {item.body}
                </Card>
              </List.Item>
            )}
          />
        </Card>
      </Content>
    </Layout>
  );
}
