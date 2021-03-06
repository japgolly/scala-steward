package org.scalasteward.core.github.data

import io.circe.Decoder
import io.circe.syntax._
import org.scalasteward.core.github.data.PullRequestState.{Closed, Open}
import org.scalatest.{Assertion, FunSuite, Matchers}

class PullRequestStateTest extends FunSuite with Matchers {
  def roundTrip(state: PullRequestState): Assertion =
    Decoder[PullRequestState].decodeJson(state.asJson) shouldBe Right(state)

  test("round-trip") {
    roundTrip(Open)
    roundTrip(Closed)
  }
}
