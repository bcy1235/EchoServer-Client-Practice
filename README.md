# EchoServer-Client-Practice
JAVA 소켓/네트워크 관련 라이브러리를 활용한, 간단한 에코 서버-클라 예제입니다.

서버는 다음 3가지 버전이 존재합니다.

- SingleVersion
 1개의 클라와 1개의 서버의 메인 쓰레드가 연결되어, 클라에서 보낸 문자열을 주고 받습니다. 따라서, 하나의 클라의 연결이 종료되기전까지 다른 클라에서 연결할 수 없습니다.
  
- MultiVersion
 여러 개의 클라가 보낸 연결마다 쓰레드를 생성하여, 서버 각 1개 쓰레드 - 클라의 형태로 문자열을 주고 받습니다. 연결된 클라의 수만큼 쓰레드가 추가 생성되며, 이는 곧 메모리 부족 또는 context switch에 과도한 시간비용이 발생하는 원인이 될 수 있습니다.

- MultiNioVersion
 여러 개의 클라가 연결을 보내면, 각 연결을 SocketChannel과 짝을 짓습니다. 그 후, SocketChannel을 Selector에 등록하고, 각 SocketChannel에서 인풋이 감지되면 Selector가 해당 SocketChannel만을 선별하여 처리할 수 있게 도와줍니다.
결론적으로, 연결을 기다리는 Listening 역할을 하는 메인 쓰레드와, 인풋이 감지된 SocketChannel을 선별하여 실질적인 Echo를 해주는 쓰레드, 단 2개의 쓰레드만으로 여러 클라의 요청을 처리할 수 있습니다.


 이 중에서, 가장 발달한 구조는 MultiNioVersion이지만, 이 역시 성능상에 문제가 있을 것으로 보입니다.
왜냐하면, Listening 역할을 하는 1개의 쓰레드는 차치하더라도, Selector로부터 선별된 SocketChannel들을 단 1개의 쓰레드가 감당해야하기 때문에 연결이 많아질수록 Echo 역할을하는 쓰레드의 부담이 커지기 때문입니다.
이를 해결하기 위해서는 여러 개의 쓰레드가 Echo 부분을 담당하도록 하는 것이 성능상으로 적절하다고 판단되며, 이때, 멀티 쓰레드의 개수나 생명 주기를 결정하는 것 역시 골치 아픈 문제가 될 수 있습니다.

자세한 내용은 채팅서버 성능 튜닝 프로젝트에서 다루도록 하겠습니다.
