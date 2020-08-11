package cinema.test.exception

class TestFailedException[Out](val out: Out) extends RuntimeException(out.toString)