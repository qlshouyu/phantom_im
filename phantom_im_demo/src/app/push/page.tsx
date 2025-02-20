'use client';

import React, { useState, useEffect } from 'react';
import { Layout, Form, Input, Button, Card, List, message } from 'antd';
import PhantomIM,{ Client, Message } from '@/libs/phantom_im';
import styles from './page.module.css';

const { Content, Sider } = Layout;

export default function PushTest() {
  const [messages, setMessages] = useState<string[]>([]);
  const [form] = Form.useForm();

  // 连接WebSocket
  useEffect(() => {
    PhantomIM.init('', 'test');

    const client=new Client('test',{
      onConnected:()=>{
        console.log('连接成功');  
        client.setTags(["t1"])
      },
      onMessage: (msg: Message) => {
        setMessages((prevMessages) => [...prevMessages, msg.b]);
      }
    });
   
  }, []);

  // 发送推送消息
  const handleSubmit = async (values: { message: string }) => {
    try {
      const msg={
        type: 'tag',
        body:values.message,
        to:['t1']
      }
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
                <Card type="inner" title={`消息 ${index + 1}`} style={{ width: '100%' }}>
                  {item}
                </Card>
              </List.Item>
            )}
          />
        </Card>
      </Content>
    </Layout>
  );
}
