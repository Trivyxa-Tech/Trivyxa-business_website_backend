import express from 'express'
import cors from 'cors'
import nodemailer from 'nodemailer'
import dotenv from 'dotenv'

dotenv.config()

const app = express()

app.use(cors())
app.use(express.json())

const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: process.env.EMAIL_USER,
    pass: process.env.EMAIL_PASS,
  },
})

app.post('/api/contact', async (req, res) => {
  try {
    const {
      firstName,
      lastName,
      email,
      phone,
      services,
      projectDetails,
      budget,
    } = req.body

    // Email content
    const mailOptions = {
      from: `"Contact Form" <${process.env.EMAIL_USER}>`,
      to: process.env.EMAIL_USER, // where YOU receive mail
      replyTo: email, // reply goes to user
      subject: '📩 New Contact Form Submission',
      html: `
        <h2>New Contact Request</h2>
        <p><strong>Name:</strong> ${firstName} ${lastName || ''}</p>
        <p><strong>Email:</strong> ${email}</p>
        <p><strong>Phone:</strong> ${phone || 'N/A'}</p>
        <p><strong>Service:</strong> ${services || 'N/A'}</p>
        <p><strong>Budget:</strong> ${budget || 'N/A'}</p>
        <p><strong>Project Details:</strong></p>
        <p>${projectDetails}</p>
      `,
    }

    await transporter.sendMail(mailOptions)

    res.status(200).json({
      success: true,
      message: 'Email sent successfully',
    })
  } catch (error) {
    console.error('Email error:', error)
    res.status(500).json({
      error: 'Failed to send email',
    })
  }
})

const PORT = process.env.PORT || 3000

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`)
})
